package com.example.site_reservation_system.service.snowflake;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.site_reservation_system.mapper.snowflake.SnowflakeMapper;
import com.example.site_reservation_system.model.snowflake.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Created By Alan on 2022/2/10
 * */
@Service
public class SnowflakeService {
    private static final QueryWrapper<Snowflake> query = new QueryWrapper<>();

    @Autowired
    private SnowflakeMapper snowflakeMapper;

    public Integer insert(Snowflake item) {
        return snowflakeMapper.insert(item);
    }
}