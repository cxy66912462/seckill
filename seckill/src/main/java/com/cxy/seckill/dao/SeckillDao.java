package com.cxy.seckill.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cxy.seckill.entity.Seckill;

/**
 * description:秒杀接口
 * 2016年12月12日 下午2:20:05
 * @author cxy
 */
public interface SeckillDao {

	/**
	 * description:减库存
	 * 2016年12月12日 下午2:20:49
	 * @author cxy
	 * @param seckillId
	 * @param killTime
	 * @return
	 */
	int reduceNumber(@Param("seckillId") Long seckillId,@Param("killTime") String killTime);
	
	/**
	 * description:查询秒杀对象
	 * 2016年12月12日 下午2:21:49
	 * @author cxy
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(Long seckillId);
	
	/**
	 * description:根据偏移量查询秒杀商品列表
	 * 2016年12月12日 下午2:22:12
	 * @author cxy
	 * @param offet
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offest") int offset,@Param("limit") int limit);
	
//	List<Seckill> queryAll();
	
	
	/**
	 * description:使用存储过程执行秒杀
	 * 2016年12月12日 下午2:22:30
	 * @author cxy
	 * @param paramMap
	 * @return 
	 */
	void killByProcedure(Map<String,Object> paramMap);
}
