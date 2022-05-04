package com.example.site_reservation_system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.site_reservation_system.mapper.SysConfigMapper;
import com.example.site_reservation_system.model.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/*
 * Created By Alan on 2022/2/13
 * */
@Service
public class SysConfigService {

    private static final QueryWrapper<SysConfig> query = new QueryWrapper<>();

    @Autowired
    private SysConfigMapper sysConfigMapper;

    public SysConfig queryById(Serializable id){
        return sysConfigMapper.selectById(id);
    }
}