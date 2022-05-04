package com.example.site_reservation_system.service.visitor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.site_reservation_system.mapper.visitor.VisitorMapper;
import com.example.site_reservation_system.model.visitor.Visitor;
import com.example.site_reservation_system.util.result.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class VisitorService {

    private static final QueryWrapper<Visitor> query = new QueryWrapper<>();

    @Autowired
    private VisitorMapper userMapper;

    public Integer insert(Visitor user) {
        return userMapper.insert(user);
    }

    public Integer deleteById(Visitor user) {
        return userMapper.deleteById(user);
    }

    public Visitor queryById(Serializable id) {
        return userMapper.selectById(id);
    }

    public Integer updateById(Visitor user) {
        return userMapper.updateById(user);
    }

    public IPage<Visitor> queryAll(Integer page, Integer size) {
        query.clear();
        query.isNotNull("id");
        return userMapper.selectPage(new Page<Visitor>(page, size), query);
    }
}