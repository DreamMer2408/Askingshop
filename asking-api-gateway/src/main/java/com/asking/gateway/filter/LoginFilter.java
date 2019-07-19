package com.asking.gateway.filter;

import com.asking.auth.utils.JwtUtils;
import com.asking.gateway.config.FilterProperties;
import com.asking.gateway.config.JwtProperties;
import com.asking.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LoginFilter
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/19 18:18
 * @Version 1.0
 **/
@Component
//@EnableConfigurationProperties(JwtProperties.class)
public class LoginFilter extends ZuulFilter {
    @Autowired
    private JwtProperties properties;
    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request=context.getRequest();
        String requestUri=request.getRequestURI();
        return !isAllowPath(requestUri);
    }
    private boolean isAllowPath(String requestUri){
        boolean flag=false;

        for (String path:filterProperties.getAllowPaths()) {
            if (requestUri.startsWith(path)){
                flag=true;
                break;
            }
        }
        return flag;
    }
    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext context=RequestContext.getCurrentContext();
        //获取request
        HttpServletRequest request=context.getRequest();
        //获取token
        String token = CookieUtils.getCookieValue(request, properties.getCookieName());
        try {
            JwtUtils.getInfoFromToken(token,properties.getPublicKey());
        }catch (Exception e){
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;
    }
}
