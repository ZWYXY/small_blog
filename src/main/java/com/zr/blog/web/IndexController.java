package com.zr.blog.web;

import com.zr.blog.service.BlogService;
import com.zr.blog.service.TagService;
import com.zr.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class IndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    private static final String INDEX = "index";
    private static final String SEARCH = "search";
    private static final String BLOG = "blog";


    @GetMapping("/")
    public String index(
            @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
            Pageable pageable, Model model) {
        model.addAttribute("page",blogService.blogList(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return INDEX;
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.blogList("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return SEARCH;
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return BLOG;
    }

    @GetMapping("/footer/new_blog")
    public String newblogs(Model model) {
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newBlogList";
    }

}
