package com.asking.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName FilterProperties
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/19 18:39
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "asking.filter")
public class FilterProperties {
    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
