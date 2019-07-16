package com.asking.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisTest
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/15 19:45
 * @Version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("key1","value1");
        String val=redisTemplate.opsForValue().get("key1");
        System.out.println(val);
    }

    @Test
    public void testRedis2(){
        redisTemplate.opsForValue().set("key2","value2",5, TimeUnit.HOURS);
    }


}
