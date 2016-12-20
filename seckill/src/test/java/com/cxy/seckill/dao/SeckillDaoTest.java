package com.cxy.seckill.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cxy.seckill.entity.Seckill;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;

/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	//注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;
    
    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int updateCount = seckillDao.reduceNumber(1000L, sdf.format(killTime));
        System.out.println("updateCount=" + updateCount);
    }
    

    @Test
    public void testQueryById() throws Exception {
        Long id = (long) 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }
    
    
    @Test
    public void testQueryAll() throws Exception {
//        List<Seckill> seckill = seckillDao.queryAll2();
        List<Seckill> seckill = seckillDao.queryAll(0, 4);
        System.out.println(seckill);
        System.out.println(seckill.size());
    }
    
    @Test
    public void killByProcedure() throws Exception {
        Map<String,Object> param = new HashMap<>();
        param.put("seckillId", 1003);
        param.put("phone", "13502178891");
        param.put("killTime", "2016-12-13");
        param.put("result", "");
        seckillDao.killByProcedure(param);
        System.out.println(param.get("result"));
    }
	
}
