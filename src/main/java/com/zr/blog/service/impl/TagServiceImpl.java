package com.zr.blog.service.impl;

import com.zr.blog.dao.TagRepository;
import com.zr.blog.exception.NotFoundException;
import com.zr.blog.po.Tag;
import com.zr.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Tag> tagList() {
        return tagRepository.findAll();
    }

    /**
     * @param ids
     */
    @Override
    public List<Tag> tagList(String ids) {
        String[] split = ids.split(",");
        List<Long> lIds = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
        return tagRepository.findAllById(lIds);
    }

    /**
     * 获取最热门的几个标签
     *
     * @param size
     * @return
     */
    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable =PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
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
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
