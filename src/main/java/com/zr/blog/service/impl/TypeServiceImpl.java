package com.zr.blog.service.impl;

import com.zr.blog.dao.TypeRepository;
import com.zr.blog.exception.NotFoundException;
import com.zr.blog.po.Type;
import com.zr.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeRepository typeRepository;

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    /**
     * 获取所有Type 不需要分页
     * @return
     */
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public Type updateType(Long id, Type type) {
        Type t;
        try {
            t = typeRepository.getOne(id);
            BeanUtils.copyProperties(type, t);
        } catch (Exception e) {
            throw new NotFoundException("不存在该类型");
        }
        return typeRepository.save(t);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
