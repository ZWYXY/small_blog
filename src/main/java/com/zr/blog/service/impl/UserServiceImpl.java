package com.zr.blog.service.impl;

import com.zr.blog.dao.UserRepository;
import com.zr.blog.po.User;
import com.zr.blog.service.UserService;
import com.zr.blog.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository
            .findUserByUsernameAndPassword(username, MD5Util.code(password));
    }
}
