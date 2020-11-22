package com.whu.hello.controller;

import com.whu.hello.Service.UserService;
import com.whu.hello.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/other")
public class AnotherController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getAllUser() {
        List<User> userList = userService.findAllUser();

        return "index";
    }
}
