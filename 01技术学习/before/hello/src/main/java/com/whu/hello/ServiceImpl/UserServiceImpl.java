package com.whu.hello.ServiceImpl;

import com.whu.hello.Service.UserService;
import com.whu.hello.dao.User;
import com.whu.hello.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service 注解告诉spring，需要的时候要创建好对应的实例
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUser() {
        List<User> lists = userMapper.findAllUser();
        return null;
    }
}
