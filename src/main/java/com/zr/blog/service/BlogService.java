package com.zr.blog.service;

import com.zr.blog.po.Blog;
import com.zr.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> blogList(Pageable pageable);

    Page<Blog> blogList(String query, Pageable pageable);

    Page<Blog> blogList(Pageable pageable, BlogQuery blog);

    Page<Blog> blogList(Long tagId, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Blog getAndConvert(Long id);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();
}
