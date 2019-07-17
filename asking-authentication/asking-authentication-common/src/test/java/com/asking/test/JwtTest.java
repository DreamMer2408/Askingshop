package com.asking.test;

import com.asking.auth.entity.UserInfo;
import com.asking.auth.utils.JwtUtils;
import com.asking.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ClassName JwtTest
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/16 23:34
 * @Version 1.0
 **/


public class JwtTest {
    private static final String pubKeyPath="D:\\temp\\rsa\\rsa.pub";
    private static final String priKeyPath="D:\\temp\\rsa\\rsa.pri";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * 生成公钥私钥
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception{
        RsaUtils.generateKey(pubKeyPath,priKeyPath,"234");
    }
    @Before
    public void testGetRsa() throws Exception{
        publicKey=RsaUtils.getPublicKey(pubKeyPath);
        privateKey=RsaUtils.getPrivateKey(priKeyPath);
    }

    /**
     * 生成token
     * @throws Exception
     */
    @Test
    public void testGenerateToken() throws Exception{
        String token= JwtUtils.generateToken(new UserInfo(20L,"jack"),privateKey,5);
        System.out.println("token="+token);
    }

    /**
     * 解析token
     * @throws Exception
     */
    @Test
    public void testParseToken() throws Exception{
        String token="eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU2Mzc3NzI2N30.O5CCvMn0SUFBCahylkT77Ay_yFYmzpv92Q70e3WE050SVq44_T25BaDCJzeocM4QUp4mRO2shPhtt9_E36s5XnojIvkUCAG7iZgGZDefl_q7ZMG1ycAM59dSUcRoXjdSfJJSJRqwRmd9hk65HF8RQxZT98IHNFzeyIM7ZeorBz7j5XICJk--1tZhC8PfkdirQFoySePoPs2hh9GUW3q16faEXGcllv6ds1f8zBel07Or1OSrW-ww4RfRyJmMiIoqCV3RLpB4kpGAPzdLXm84caWuJ7Boix53omMEJ4RWajZXxAsxYtzFDnodaAhwXzLb86Wvr8zP2vi4GQWnRt5spw";
        UserInfo userInfo=JwtUtils.getInfoFromToken(token,publicKey);
        System.out.println("id="+userInfo.getId());
        System.out.println("userName="+userInfo.getUsername());
    }
}
