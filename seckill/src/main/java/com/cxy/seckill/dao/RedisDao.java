package com.cxy.seckill.dao;

import com.cxy.seckill.entity.Seckill;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private final JedisPool jedisPool;
	
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip, port);
	}

	private  RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	//从redis获取秒杀地址
	public Seckill getSeckill(Long seckillId) {
		try {
			Jedis jedis = jedisPool.getResource();
			//redis并没有实现内部序列化操作
	        // get-> byte[] -> 反序列化 ->Object(Seckill)
	        // 采用自定义序列化
	        //protostuff : pojo.
			try {
				String key = "seckillId:"+seckillId;
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null){
					//创建空对象 scheme创建空对象
					Seckill seckill = schema.newMessage();
					//反序列化字节数组到对象,此时seckill被反序列化
					ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			} catch (Exception e) {
			}finally {
				jedis.close();
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				//获取jedis
				//生成key
				String key = "seckillId:"+seckill.getSeckillId();
				//序列化seckill对象
				byte[] byteArray = ProtobufIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//setex 设置超时缓存  单位秒 60*60 1小时
				String result = jedis.setex(key.getBytes(), 60*60, byteArray);
				return result;
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				jedis.close();
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	
}
