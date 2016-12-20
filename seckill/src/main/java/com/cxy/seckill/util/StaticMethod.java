package com.cxy.seckill.util;

import org.springframework.util.DigestUtils;

public class StaticMethod {

	//md5盐值字符串,用于混淆MD5
    private final static String salt = "sadkfjalsdjfalksj23423^&*^&%&!EBJKH#e™£4";
    
    public static String getMD5(Long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
