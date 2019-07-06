package com.asking.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName AskingCorsConfiguration
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/4 18:48
 * @Version 1.0
 **/
@Configuration
public class AskingCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.addAllowedOrigin("http://manage.askingshop.com");
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("HEAD");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource configurationSource=new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);

        return new CorsFilter(configurationSource);
    }
}
