package com.zr.blog.service;

import com.zr.blog.po.Tag;
import com.zr.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {


    /**
     * 获取单个 Tag
     * @param id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 获取 Tag 列表
     * @param pageable
     * @return
     */
    Page<Tag> TagList(Pageable pageable);

    /**
     * 根据 Tag 名称获取Tag
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 新增 Tag
     * @param tag
     * @return
     */
    Tag save(Tag tag);

    /**
     * 修改 Tag
     * @param id
     * @param tag
     * @return
     */
    Tag updateTag(Long id, Tag tag);

    /**
     * 删除Tag
     * @param id
     */
    void deleteTag(Long id);




}
