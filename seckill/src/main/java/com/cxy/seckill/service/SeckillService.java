package com.cxy.seckill.service;

import java.text.ParseException;
import java.util.List;

import com.cxy.seckill.dto.Exposer;
import com.cxy.seckill.dto.SeckillExecution;
import com.cxy.seckill.entity.Seckill;
import com.cxy.seckill.exception.RepeatKillException;
import com.cxy.seckill.exception.SeckillCloseException;
import com.cxy.seckill.exception.SeckillException;

/**
 * description:秒杀业务
 * 业务接口:站在"使用者"角度设计接口
 * 三个方面:方法定义粒度,参数,返回类型(return 类型/异常)
 * 2016年12月13日 下午2:16:39
 * @author cxy
 */
public interface SeckillService {
	
	/**
	 * description:查询所有秒杀记录
	 * 2016年12月13日 下午2:18:53
	 * @author cxy
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * description:查询单个秒杀记录
	 * 2016年12月13日 下午2:19:20
	 * @author cxy
	 * @param seckillId
	 * @return
	 */
	Seckill getById(Long seckillId);

	/**
	 * description:秒杀开启输出秒杀接口地址,
     * 			       否则输出系统时间和秒杀时间
	 * 2016年12月13日 下午2:19:53
	 * @author cxy
	 * @param seckillId
	 * @return
	 * @throws ParseException 
	 */
	Exposer exportSeckillUrl(Long seckillId) throws ParseException;

	/**
	 * description:执行秒杀操作
	 * 2016年12月13日 下午2:20:29
	 * @author cxy
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckill(Long seckillId, String userPhone, String md5)
	        throws SeckillException,RepeatKillException,SeckillCloseException;
	
	
	/**
	 * description:执行秒杀操作by 存储过程
	 * 2016年12月13日 下午2:20:57
	 * @author cxy
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckillProcedure(Long seckillId, String userPhone, String md5);
}
