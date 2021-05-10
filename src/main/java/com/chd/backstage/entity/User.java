package com.chd.backstage.entity;

import com.google.common.base.MoreObjects;

/**
 * shiro权限验证用到的用户类
 */
public class User {
    private int id; //用户id
    private String username; //用户名
    private String password; //密码

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", username)
                .add("password", password)
                .toString();
    }
}