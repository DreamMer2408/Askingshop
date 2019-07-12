package com.asking.user.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.asking.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.spring.annotation.MapperScan;

import javax.xml.ws.Action;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 10:05
 * @Version 1.0
 **/
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data")String data,@PathVariable("type")int type){
        Boolean  result=userService.checkData(data,type);
        logger.info("结束检验用户名或手机号,result={}",result);
        if (result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

}
