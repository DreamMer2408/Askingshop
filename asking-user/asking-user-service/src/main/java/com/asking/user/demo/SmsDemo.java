package com.asking.user.demo;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SmsDemo
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 11:20
 * @Version 1.0
 **/
public class SmsDemo {
    //产品名称：云通信短信API产品，开发者无需替换
    private static final String product="Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    //此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIAmUoi6ZhVUJn";
    static final String accessKeySecret = "5YuCHRrFOV0HtubpEY1G0cEA0zjP5e";

    public static void main(String[] args) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //短信签名名称
        request.putQueryParameter("SignName","AskingShop商城");
        //短信模板ID
        request.putQueryParameter("TemplateCode","SMS_170330126");
        request.putQueryParameter("PhoneNumbers","17638102410");
        Map<String,String> map=new HashMap<>();
        map.put("code","1234");
        JSON json=(JSON) JSON.toJSON(map);
        request.putQueryParameter("TemplateParam",json.toJSONString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
