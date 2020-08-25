package com.zr.blog.service;

import com.zr.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);

}
