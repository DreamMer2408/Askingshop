package com.asking.user.service;

import com.asking.user.mapper.UserMapper;
import com.asking.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 10:08
 * @Version 1.0
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);
    /**
     * 检查用户名和手机号是否可用
     * @param data
     * @param type  1，用户名；2，手机号
     * @return
     */
    public Boolean checkData(String data,int type){
        logger.info("开始检验用户名或手机号,type={},data={}",type,data);
        User user=new User();
        switch (type){
            case 1:user.setUsername(data);break;
            case 2:user.setPhone(data);break;
            default:return  null;
        }
        return userMapper.selectCount(user)==0;
    }
}
