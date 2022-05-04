package com.example.site_reservation_system.service.snowflake;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.site_reservation_system.mapper.snowflake.StaticResourceMapper;
import com.example.site_reservation_system.model.snowflake.StaticResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Created By Alan on 2022/2/10
 * */
@Service
public class StaticResourceService {

    private static final QueryWrapper<StaticResource> query = new QueryWrapper<>();

    @Autowired
    private StaticResourceMapper staticResourceMapper;

    public Integer insert(StaticResource item) {
        return staticResourceMapper.insert(item);
    }
}