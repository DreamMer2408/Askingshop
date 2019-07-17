package com.asking.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName AskingAuthApplication
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/16 23:23
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AskingAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AskingAuthApplication.class);
    }
}
