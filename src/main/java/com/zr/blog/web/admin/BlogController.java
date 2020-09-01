package com.zr.blog.web.admin;


import com.alibaba.fastjson.JSON;
import com.zr.blog.exception.NotFoundException;
import com.zr.blog.po.Blog;
import com.zr.blog.po.User;
import com.zr.blog.service.BlogService;
import com.zr.blog.service.TagService;
import com.zr.blog.service.TypeService;
import com.zr.blog.vo.BlogQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/publish";
    private static final String BLOG_LIST = "admin/blogManage";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

//    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

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
        Page<Blog> blogs = blogService.blogList(pageable, blogQuery);
        model.addAttribute("page", blogs);
        log.error("分页信息{}", JSON.toJSONString(blogs));
        return "/admin/blogManage :: blogList";
    }

    /**
     * 跳转到博客输入界面
     * @param model
     * @return
     */
    @GetMapping("/blog/input")
    public String input(Model model) {
        this.setTypeAndTags(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    private void setTypeAndTags(Model model) {
        model.addAttribute("tags", tagService.tagList());
        model.addAttribute("types", typeService.listType());
    }

    /**
     * 跳转到publish页面，并填充指定id的blog数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}/input")
    public String edit(@PathVariable Long id,  Model model) {
        this.setTypeAndTags(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    /**
     * 保存/提交/修改 blog
     * @return
     */
    @PostMapping("/blog")
    public String post(
            @Valid Blog blog, HttpSession session, RedirectAttributes redirectAttributes, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) { return INPUT; }

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.tagList(blog.getTagsId()));
        Blog b;
        if(blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if(b == null) {
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作客成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes ) {
        try {
            blogService.deleteBlog(id);
        } catch (Exception e) {
            throw new NotFoundException("找不到对应ID的blog，该博客可能已经被删除");
        }
        redirectAttributes.addFlashAttribute("message", "操作成功");
        return REDIRECT_LIST;
    }


}
