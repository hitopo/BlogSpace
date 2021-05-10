package com.chd.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.chd.web.constant.ForwardConstant.FWD_SECURITY_SYSTEM_HOME;
import static com.chd.web.constant.URIConstant.URL_HOME;
import static com.chd.web.constant.URIConstant.URL_HOME_LOGOUT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * 后台管理控制器
 */
@Controller
public class HomePageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);

    /**
     * 进入后台管理系统主页
     */
    @RequestMapping(value = {URL_HOME}, method = {GET})
    public ModelAndView home() {
        LOGGER.info("enter home page");
        return new ModelAndView(FWD_SECURITY_SYSTEM_HOME);
    }

    /**
     * 用户退出
     */
    @RequestMapping(value = {URL_HOME_LOGOUT})
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        LOGGER.info("account logout");
        // 从session中删除account信息即可
        session.removeAttribute("account");
        LOGGER.info("account={}", session.getAttribute("account"));
        // 重新返回主页
        response.sendRedirect("/actions/article/list");
    }

}
