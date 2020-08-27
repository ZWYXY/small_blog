package com.zr.blog.dao;

import com.zr.blog.po.Tag;
import com.zr.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 通过 name 查询标签名称
     * @param name
     * @return
     */
    Tag findByName(String name);

}
