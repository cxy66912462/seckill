package com.cxy.seckill.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxy.seckill.common.SeckillStatEnum;
import com.cxy.seckill.dao.SeckillDao;
import com.cxy.seckill.dao.SuccessKilledDao;
import com.cxy.seckill.dto.Exposer;
import com.cxy.seckill.dto.SeckillExecution;
import com.cxy.seckill.entity.Seckill;
import com.cxy.seckill.entity.SuccessKilled;
import com.cxy.seckill.exception.RepeatKillException;
import com.cxy.seckill.exception.SeckillCloseException;
import com.cxy.seckill.exception.SeckillException;
import com.cxy.seckill.service.SeckillService;
import com.cxy.seckill.util.StaticMethod;


@Service("seckillService")
public class SeckillServiceImpl implements SeckillService{
	
//	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Logger logger =  LogManager.getLogger(SeckillServiceImpl.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

    //注入Service依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

//    @Autowired
//    private RedisDao redisDao;

	@Override
	public List<Seckill> getSeckillList() {
		List<Seckill> list = seckillDao.queryAll(0, Integer.MAX_VALUE);
		return list;
	}

	@Override
	public Seckill getById(Long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		return seckill;
	}

	@Override
	public Exposer exportSeckillUrl(Long seckillId) throws ParseException {
		 // 优化点:缓存优化:超时的基础上维护一致性
        //1：访问redis
//        Seckill seckill = redisDao.getSeckill(seckillId);
//        if (seckill == null) {
//            //2:访问数据库
		Seckill seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } 
//                else {
//                //3:放入redis
//                redisDao.putSeckill(seckill);
//            }
//        }

        Date startTime = sdf.parse(seckill.getStartTime());
        Date endTime = sdf.parse(seckill.getEndTime());
        //系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(),
                    endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5 = StaticMethod.getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
	}

	/*
     * 使用注解控制事务方法的优点:
     * 1:开发团队达成一致约定,明确标注事务方法的编程风格。
     * 2:保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部.
     * 3:不是所有的方法都需要事务,如只有一条修改操作,只读操作不需要事务控制.
     */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(Long seckillId, String userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		//md5不存在或者不匹配
		if(StringUtils.isBlank(md5) || !md5.equals(StaticMethod.getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑:减库存+记录购买行为 事务控制
		Date now = new Date();
		try {
			//seckillId+userPhone 组合唯一
			int insertSuccessKilled = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertSuccessKilled<=0){
				//重复秒杀
				throw new RepeatKillException("seckill repeat ");
			}else{
				//减库存
				int reduceNumber = seckillDao.reduceNumber(seckillId, sdf.format(now));
				if(reduceNumber<=0){
					//update 失败,秒杀结束,回滚
					throw new SeckillCloseException("seckill is closed");
				}else{
					//秒杀成功 commit
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常 转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
	}

	@Override
	public SeckillExecution executeSeckillProcedure(Long seckillId, String userPhone, String md5) {
		// md5不存在或者不匹配
		if (StringUtils.isBlank(md5) || !md5.equals(StaticMethod.getMD5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seckillId", seckillId);
		paramMap.put("phone", userPhone);
		paramMap.put("killTime", sdf.format(killTime));
		paramMap.put("result", null);
		try {
			seckillDao.killByProcedure(paramMap);
			int result = (int) paramMap.get("result");
			if(result == 1){
				SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
			}else{
				return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}

}
