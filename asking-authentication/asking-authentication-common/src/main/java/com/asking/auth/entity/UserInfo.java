package com.asking.auth.entity;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/16 23:30
 * @Version 1.0
 **/
public class UserInfo {
    private Long id;

    private String username;

    public UserInfo() {
    }

    public UserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
