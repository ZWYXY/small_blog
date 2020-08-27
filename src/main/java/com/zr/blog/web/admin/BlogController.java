package com.zr.blog.web.admin;


import com.zr.blog.service.BlogService;
import com.zr.blog.service.TypeService;
import com.zr.blog.vo.BlogQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @GetMapping("/blogs")
    public String list(
            @PageableDefault(size =  2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
            Pageable pageable, Model model, BlogQuery blogQuery) {

        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.blogList(pageable, blogQuery));
        return "/admin/blogManage";
    }

    @PostMapping("/blogs/search")
    public String search(
            @PageableDefault(size =  2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                    Pageable pageable, Model model, BlogQuery blogQuery) {
        model.addAttribute("page", blogService.blogList(pageable, blogQuery));
        return "/admin/blogManage :: blogList";
    }

}
