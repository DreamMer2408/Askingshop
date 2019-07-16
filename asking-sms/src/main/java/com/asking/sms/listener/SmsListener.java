package com.asking.sms.listener;

import com.aliyuncs.CommonResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.utils.StringUtils;
import com.asking.sms.pojo.SmsProperties;
import com.asking.sms.utils.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.Action;
import java.util.Map;

/**
 * @ClassName SmsListener
 * @Description 短信服务监听器
 * @Author wangs
 * @Date 2019/7/11 16:04
 * @Version 1.0
 **/
@Component
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private SmsProperties smsProperties;

    private static final Logger logger= LoggerFactory.getLogger(SmsListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "asking.sms.queue",durable = "true"),
            exchange = @Exchange(value = "asking.sms.exchange",ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}
    ))
    public void listenSms(Map<String,String> msg){
        if (msg==null||msg.size()<=0){
            return;
        }

        String phone=msg.get("phone");
        String code=msg.get("code");
        logger.info("开始发送短信，phone:{},code:{}",phone,code);
        if (StringUtils.isNotEmpty(phone)&&StringUtils.isNotEmpty(code)){
            try {
                logger.info("123");
                CommonResponse response=smsUtil.sendSms(phone,code,smsProperties.getSignName(),smsProperties.getVerifyCodeTemplate());
                logger.info(response.toString());
            }catch (ClientException e){
                logger.info("发送短信失败,{}",e.toString());
                return;
            }
        }else {
            logger.info("手机号或code为空");
            return;
        }

    }
}
