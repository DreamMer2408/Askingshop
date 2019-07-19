package com.asking.gateway.config;

import com.asking.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @ClassName JwtPropertities
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/19 18:35
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "asking.jwt")
public class JwtProperties {
    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 公钥地址
     */
    @Value("${asking.jwt.pubKeyPath}")
    private String pubKeyPath;

    /**
     * cookie名字
     */
    @Value("${asking.jwt.cookieName}")
    private String cookieName;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }


    /**
     * @PostConstruct :在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            // 获取公钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            logger.error("获取公钥失败！", e);
            throw new RuntimeException();
        }
    }
}

