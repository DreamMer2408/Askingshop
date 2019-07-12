package com.askingshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName com.askingshop.AskingUploadService
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/5 20:08
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class AskingUploadService {
    public static void main(String[] args) {
        SpringApplication.run(AskingUploadService.class,args);
    }
}
