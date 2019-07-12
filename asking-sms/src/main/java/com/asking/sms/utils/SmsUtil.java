package com.asking.sms.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.asking.sms.pojo.SmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SmsUtil
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 15:55
 * @Version 1.0
 **/
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtil {

    @Autowired
    private SmsProperties smsProperties;

    private static final Logger logger= LoggerFactory.getLogger(SmsUtil.class);
    //产品名称：云通信短信API产品，开发者无需替换
    private static final String product="Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    //此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIAmUoi6ZhVUJn";
    static final String accessKeySecret = "5YuCHRrFOV0HtubpEY1G0cEA0zjP5e";

    public CommonResponse sendSms(String phone,String code,String signName,String template) throws ClientException{
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //短信签名名称
        request.putQueryParameter("SignName",signName);
        //短信模板ID
        request.putQueryParameter("TemplateCode",template);
        request.putQueryParameter("PhoneNumbers",phone);
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        JSON json=(JSON) JSON.toJSON(map);
        request.putQueryParameter("TemplateParam",json.toJSONString());
        CommonResponse response = client.getCommonResponse(request);
        logger.info(response.getData());
        return response;
    }

}
