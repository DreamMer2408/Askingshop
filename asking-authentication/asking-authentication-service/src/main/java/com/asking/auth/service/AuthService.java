package com.asking.auth.service;

import com.asking.auth.client.UserClient;
import com.asking.auth.entity.UserInfo;
import com.asking.auth.properties.JwtProperties;
import com.asking.auth.utils.JwtUtils;
import com.asking.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AuthService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/17 15:18
 * @Version 1.0
 **/
@Service
public class AuthService {
    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    private static final Logger logger= LoggerFactory.getLogger(AuthService.class);

    /**
     * 用户授权
     * @param username
     * @param password
     * @return
     */
    public String authentication(String username,String password){
        logger.info("开始用户授权,username:{},password:{}",username,password);
        try {
            User user=userClient.queryUser(username,password);
            logger.info("查询到用户信息,user:{}",user);
            if (user==null){
                return null;
            }
            String token= JwtUtils.generateToken(new UserInfo(user.getId(),user.getUsername()),jwtProperties.getPrivateKey(),jwtProperties.getExpire());
            logger.info("生成token,{}",token);
            return token;
        }catch (Exception e){
            logger.info("生成token错误");
            e.printStackTrace();
            return null;
        }
    }

}
