package com.asking.sms.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SmsProperties
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 12:03
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "asking.sms")
public class SmsProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String verifyCodeTemplate;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVerifyCodeTemplate() {
        return verifyCodeTemplate;
    }

    public void setVerifyCodeTemplate(String verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }
}
