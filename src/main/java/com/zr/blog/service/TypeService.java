package com.zr.blog.service;

import com.zr.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    Type save(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();

    Page<Type> listType(Pageable pageable);

    List<Type> listTypeTop(Integer size);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

}
