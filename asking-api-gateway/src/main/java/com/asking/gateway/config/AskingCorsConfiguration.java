package com.asking.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName AskingCorsConfiguration
 * @Description 处理跨域请求的过滤器
 * @Author wangs
 * @Date 2019/7/4 18:48
 * @Version 1.0
 **/
@Configuration
public class AskingCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        //添加CORs配置信息
        CorsConfiguration configuration=new CorsConfiguration();

        //允许的域，不要写*，否则cookie不能用
        configuration.addAllowedOrigin("http://manage.askingshop.com");
        //是否发送cookie信息
        configuration.setAllowCredentials(true);
        //允许的请求方式
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("HEAD");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");
        //允许的头信息
        configuration.addAllowedHeader("*");

        //添加映射路径，这里是拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);
        //返回新的corsFilter
        return new CorsFilter(configurationSource);
    }
}
