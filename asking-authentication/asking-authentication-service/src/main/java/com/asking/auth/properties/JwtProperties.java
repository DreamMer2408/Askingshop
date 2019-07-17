package com.asking.auth.properties;

import com.asking.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ClassName JwtProperties
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/17 14:44
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "asking.jwt")
public class JwtProperties {
    @Value("${asking.jwt.secret}")
    private String secret;
    @Value("${asking.jwt.pubKeyPath}")
    private String pubKeyPath;
    @Value("${asking.jwt.priKeyPath}")
    private String priKeyPath;
    @Value("${asking.jwt.expire}")
    private int expire;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String cookieName;
    private Integer cookieMaxAge;
    private static final Logger logger= LoggerFactory.getLogger(JwtProperties.class);

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(Integer cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    /**
     * 构造方法之后执行该方法
     */
    @PostConstruct
    public void init(){
        try {
            File pubKey=new File(pubKeyPath);
            File priKey=new File(priKeyPath);
            if (!pubKey.exists()||!priKey.exists()){
                RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);
            }
            publicKey=RsaUtils.getPublicKey(pubKeyPath);
            privateKey=RsaUtils.getPrivateKey(priKeyPath);
        }catch (Exception e){
            logger.info("初始化公钥和私钥错误",e);
        }
    }
}
