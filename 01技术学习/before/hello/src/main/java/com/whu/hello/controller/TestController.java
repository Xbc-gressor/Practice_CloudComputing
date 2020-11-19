package com.whu.hello.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("content")
    public String content(HttpServletRequest request){
        String name = request.getParameter("name");
        if (StringUtils.isBlank(name)){
            request.setAttribute("msg", "name 不能为空！");
            return "error";
        }
        log.info("request URL: {}", request.getRequestURL().toString());
        log.info("name: {}", request.getParameter("name"));
        log.info("method: {}", request.getMethod());
        request.setAttribute("username", name);
        return "index";
    }

//    @RequestMapping("login")
    @PostMapping("login")
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            request.setAttribute("msg", "用户名或密码 不能为空！");
            return "error";
        }

        if (!username.equals("abc") || !password.equals("123")){
            request.setAttribute("msg", "用户名或密码 错误！");
            return "error";
        }

        request.setAttribute("username", username);
        return "index";
    }
}
