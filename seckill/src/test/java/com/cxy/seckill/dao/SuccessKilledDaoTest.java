package com.cxy.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cxy.seckill.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
	@Resource
    private SuccessKilledDao successKilledDao;
	
	@Test
    public void testInsertSuccessKilled() throws Exception {
        /*
		        第一次:insertCount=1
		        第二次:insertCount=0
         */
        long id = 1001L;
        String phone = "13502181181";
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount=" + insertCount);
    }
	
	@Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id = 1001L;
        String phone = "13502181181";
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
