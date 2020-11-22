package com.whu.hello.Service;

import com.whu.hello.dao.User;

import java.util.List;

public interface UserService {
    List<User> findAllUser();
}
