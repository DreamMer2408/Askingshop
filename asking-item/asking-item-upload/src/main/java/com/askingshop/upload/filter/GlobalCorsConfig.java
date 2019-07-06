package com.askingshop.upload.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName GlobalCorsConfig
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/6 0:23
 * @Version 1.0
 **/
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        //1.添加Cors配置信息
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //1)允许的域，不要写*，否则cookies就不能用了
        corsConfiguration.addAllowedOrigin("http://manage.askingshop.com");
        //2)是否发送cookie信息
        corsConfiguration.setAllowCredentials(false);
        //3)允许的请求方式
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",corsConfiguration);
        //3.返回新的CorsFilter
        return new CorsFilter(configurationSource);
    }



}
