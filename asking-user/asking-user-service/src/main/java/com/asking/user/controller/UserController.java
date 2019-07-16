package com.asking.user.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.asking.user.pojo.User;
import com.asking.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.spring.annotation.MapperScan;

import javax.validation.Valid;
import javax.xml.ws.Action;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/11 10:05
 * @Version 1.0
 **/
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data")String data,@PathVariable("type")int type){
        Boolean result=userService.checkData(data,type);
        logger.info("结束检验用户名或手机号,result={}",result);
        if (result==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/code")
    public ResponseEntity senVerifyCode(String phone){
        logger.info("开始发送短信验证码");
        Boolean result=userService.sendVerifyCode(phone);
        if (result==null||!result){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username,@RequestParam("password")String password){
        User user=userService.queryUser(username,password);
        if (user==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code")String code){
        Boolean result=userService.register(user,code);
        if (result==null||!result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
