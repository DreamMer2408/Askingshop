package com.asking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName AskingSearchService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/10 12:47
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AskingSearchService {
    public static void main(String[] args) {
        SpringApplication.run(AskingSearchService.class);
    }
}
