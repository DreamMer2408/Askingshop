package com.asking.user.api;

import com.asking.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {
    @GetMapping("query")
    User queryUser(@RequestParam(value = "username",required = false)String username, @RequestParam(value = "password",required = false)String password);
}
