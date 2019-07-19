package com.asking.cast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName AskingCastApplication
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/18 15:29
 * @Version 1.0
 **/
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AskingCastApplication {
    public static void main(String[] args) {
        SpringApplication.run(AskingCastApplication.class);
    }
}
