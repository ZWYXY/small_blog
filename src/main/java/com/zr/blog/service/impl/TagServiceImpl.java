package com.zr.blog.service.impl;

import com.zr.blog.dao.TagRepository;
import com.zr.blog.dao.TypeRepository;
import com.zr.blog.exception.NotFoundException;
import com.zr.blog.po.Tag;
import com.zr.blog.po.Type;
import com.zr.blog.service.TagService;
import com.zr.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagRepository tagRepository;

    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    @Override
    public Page<Tag> TagList(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag one;
        try {
            one = tagRepository.getOne(id);
            BeanUtils.copyProperties(tag, one);
        } catch (Exception e) {
            throw new NotFoundException("不存在该类型");
        }
        return tagRepository.save(one);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}