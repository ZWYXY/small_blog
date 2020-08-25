package com.zr.blog.dao;

import com.zr.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    User findUserByUsernameAndPassword(String username, String password);

}
