package com.asking.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName CodeUtils
 * @Description 密码加密解密
 * @Author wangs
 * @Date 2019/7/16 11:52
 * @Version 1.0
 **/
public class CodeUtils {
    public static String passwordBcryptEncode(String username,String password){
        return new BCryptPasswordEncoder().encode(username+password);
    }
    public static Boolean passwordBcryptDecode(String rawPassword,String encodePassword){
        return new BCryptPasswordEncoder().matches(rawPassword,encodePassword);
    }
}
