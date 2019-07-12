package com.asking.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName AskingUserAppllication
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 10:13
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.asking.user.mapper")
public class AskingUserAppllication {
    public static void main(String[] args) {
        SpringApplication.run(AskingUserAppllication.class,args);
    }
}
