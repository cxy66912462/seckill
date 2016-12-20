package com.cxy.seckill;

import org.apache.logging.log4j.Logger;

public class TestLog {

	private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(TestLog.class);
	
	public static void main(String[] args) {
		logger.info("info");
//		logger.error("error");
	}
}
