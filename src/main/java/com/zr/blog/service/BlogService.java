package com.zr.blog.service;

import com.zr.blog.po.Blog;
import com.zr.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> blogList(Pageable pageable);

    Page<Blog> blogList(String query, Pageable pageable);

    Page<Blog> blogList(Pageable pageable, BlogQuery blog);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

}
