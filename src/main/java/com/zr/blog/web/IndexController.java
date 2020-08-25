package com.zr.blog.web;

import com.zr.blog.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        int i = 9/0;
//        String blog = null;
//        if(blog == null) {
//            throw new NotFoundException("博客不存在");
//        }
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/blog_manage")
    public String blogManage() {
        return "admin/blogManage";
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }
}
