package com.example.site_reservation_system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.site_reservation_system.mapper.FootballMapper;
import com.example.site_reservation_system.model.Football;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/*
 * Created By Alan on 2022/3/2
 * */
@Service
public class FootballService {

    private static final QueryWrapper<Football> queryWrapper = new QueryWrapper<>();

    @Autowired
    private FootballMapper footballMapper;

    public Football queryById(Serializable id) {
        return footballMapper.selectById(id);
    }

    public IPage<Football> queryAll(Integer page, Integer size) {
        queryWrapper.clear();
        queryWrapper.isNotNull("id");
        return footballMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    public Integer deleteById(Serializable id) {
        return footballMapper.deleteById(id);
    }

    public Integer updateById(Football item) {
        return footballMapper.updateById(item);
    }

    public Integer insert(Football item) {
        return footballMapper.insert(item);
    }
}