package com.zr.blog.service.impl;

import com.zr.blog.dao.BlogRepository;
import com.zr.blog.exception.NotFoundException;
import com.zr.blog.po.Blog;
import com.zr.blog.service.BlogService;
import com.zr.blog.util.MarkdownUtils;
import com.zr.blog.util.MyBeanUtils;
import com.zr.blog.vo.BlogQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> blogList(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> blogList(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Page<Blog> blogList(Pageable pageable, BlogQuery blogQuery) {
    return blogRepository.findAll(
        (Specification<Blog>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNotBlank(blogQuery.getTitle())) {
                predicates.add(
                    cb.like(root.get("title"), "%" +  blogQuery.getTitle() + "%" )
                );
            }
            if(blogQuery.getTypeId() != null) {
                predicates.add(
                    cb.equal(root.get("type").get("id"), blogQuery.getTypeId())
                );
            }
            if(blogQuery.isRecommend()) {
                predicates.add(
                    cb.equal(root.get("recommend"), blogQuery.isRecommend())
                );
            }
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        }, pageable
    );
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setViews(0);
        }
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one;
        try {
            one = blogRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("不存在的blog无法更新");
        }
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setUpdateTime(new Date());
        return blogRepository.save(one);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getOne(id);
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> blogList(Long tagId, Pageable pageable) {
        return blogRepository.findAll(
            (Specification<Blog>)
            (root, cq, cb) -> cb.equal(root.join("tags").get("id"),tagId),pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

}
