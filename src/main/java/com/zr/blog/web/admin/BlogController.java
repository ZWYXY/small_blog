package com.zr.blog.web.admin;


import com.zr.blog.po.Blog;
import com.zr.blog.service.BlogService;
import com.zr.blog.service.TagService;
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

    private static final String INPUT = "admin/publish";
    private static final String BLOG_LIST = "admin/blogManage";
    private static final String REDIRECT_LIST = "redirect:/admin/blogManage";

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    @GetMapping("/blogs")
    public String list(
            @PageableDefault(size =  2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
            Pageable pageable, Model model, BlogQuery blogQuery) {

        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.blogList(pageable, blogQuery));
        return BLOG_LIST;
    }

    @PostMapping("/blogs/search")
    public String search(
            @PageableDefault(size =  2, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                    Pageable pageable, Model model, BlogQuery blogQuery) {
        model.addAttribute("page", blogService.blogList(pageable, blogQuery));
        return "/admin/blogManage :: blogList";
    }

    /**
     * 跳转到博客输入界面
     * @param model
     * @return
     */
    @GetMapping("/blog/input")
    public String input(Model model) {
        model.addAttribute("tags", tagService.tagList());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }



}
