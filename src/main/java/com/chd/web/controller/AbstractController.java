package com.chd.web.controller;

import com.chd.backstage.entity.Account;

import javax.servlet.http.HttpSession;

/**
 * 定义Controller的父类
 */
public abstract class AbstractController {
    /**
     * 获取当前session下的登录的用户
     * @param session 会话
     * @return 当前登录的用户
     */
    public Account getCurrentAccount(HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            throw new RuntimeException(new NullPointerException("当前无登录用户"));
        }
        return account;
    }
}
