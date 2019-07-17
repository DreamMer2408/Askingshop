package com.asking.user.service;

import com.aliyuncs.utils.StringUtils;
import com.asking.user.mapper.UserMapper;
import com.asking.user.pojo.User;
import com.asking.utils.CodeUtils;
import com.asking.utils.JsonUtils;
import com.asking.utils.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX="user:code:phone";

    private static final String KEY_PREFIX2 = "askingshop:user:info";

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

    public Boolean sendVerifyCode(String phone){
        String code= NumberUtils.generateCode(6);
        logger.info("验证码是：{}",code);
        try {
            Map<String,String> msg=new HashMap<>();
            msg.put("phone",phone);
            msg.put("code",code);
            amqpTemplate.convertAndSend("asking.sms.exchange","sms.verify.code",msg);
            redisTemplate.opsForValue().set(KEY_PREFIX+phone,code,5, TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            logger.error("发送短信失败，phone:{},code:{}",phone,code);
            return false;
        }
    }

    /**
     * 根据用户名密码查询用户
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username,String password){
        logger.info("开始查询用户，username:{},password:{}",username,password);
        //缓存中查询
        BoundHashOperations<String,Object,Object> hashOperations=redisTemplate.boundHashOps(KEY_PREFIX2);
        String userStr=(String) hashOperations.get(username);
        logger.info("在缓存中获取到user:{}",userStr);
        User user;
        if (StringUtils.isEmpty(userStr)){
            //缓存中没查到，去数据库中查，查到放到缓存中
            logger.info("缓存中获取到的不能用啊老哥");
            User record=new User();
            record.setUsername(username);
            logger.info("不着急，老哥从数据库中再找");
            user=userMapper.selectOne(record);
            logger.info("在数据库中找到user:{}",user);
            hashOperations.put(user.getUsername(), JsonUtils.serialize(user));
            logger.info("已放到缓存中");
        }else {
            user=JsonUtils.parse(userStr,User.class);
        }

        if (user==null){
            logger.info("没找到用户");
            return null;
        }

        boolean result=CodeUtils.passwordBcryptDecode(username+password,user.getPassword());
        logger.info("result:{}",result);
        if (!result){
            logger.info("将返回null");
            return null;
        }
        return user;
    }

    public Boolean register(User user,String code){
        logger.info("开始注册用户，user:{},code:{}",user,code);
        String key=KEY_PREFIX+user.getPhone();
        //从redis中取出code
        String codeCache=redisTemplate.opsForValue().get(key);
        if (!codeCache.equals(code)){
            logger.info("验证码不对啊老弟");
            return false;
        }
        logger.info("验证码通过");
        user.setId(null);
        user.setCreated(new Date());
        String encodePassword= CodeUtils.passwordBcryptEncode(user.getUsername().trim(),user.getPassword().trim());
        logger.info("password:{}",encodePassword);
        user.setPassword(encodePassword);
        boolean result=userMapper.insertSelective(user)==1;
        logger.info("注册信息添加到数据中");
        //如果注册成功删掉redis中的code
        if (result){
            try {
                redisTemplate.delete(KEY_PREFIX+user.getPhone());
            }catch (Exception e){
                logger.error("删除缓存验证码失败,code:{}",code,e);
            }
        }
        return result;
    }
}
