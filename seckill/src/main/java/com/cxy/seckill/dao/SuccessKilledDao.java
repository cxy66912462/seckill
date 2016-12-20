package com.cxy.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.cxy.seckill.entity.SuccessKilled;

/**
 * description:
 * 2016年12月12日 下午2:23:01
 * @author cxy
 */
public interface SuccessKilledDao {
	
	/**
	 * description:插入购买明细,可过滤重复
	 * 2016年12月12日 下午2:24:17
	 * @author cxy
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccessKilled(@Param("seckillId") Long seckillId ,@Param("userPhone") String userPhone);
	
	/**
	 * description:根据id查询SuccessKilled并携带秒杀产品对象实体
	 * 2016年12月12日 下午2:24:14
	 * @author cxy
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") Long seckillId,@Param("userPhone") String userPhone);
}
