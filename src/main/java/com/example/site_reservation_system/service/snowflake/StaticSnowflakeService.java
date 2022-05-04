package com.example.site_reservation_system.service.snowflake;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.site_reservation_system.mapper.snowflake.StaticSnowflakeMapper;
import com.example.site_reservation_system.model.snowflake.StaticSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Created By Alan on 2022/2/10
 * */
@Service
public class StaticSnowflakeService {

    private static final QueryWrapper<StaticSnowflake> query = new QueryWrapper<>();

    @Autowired
    private StaticSnowflakeMapper staticSnowflakeMapper;

    public Integer insert(StaticSnowflake item) {
        return staticSnowflakeMapper.insert(item);
    }

    public List<StaticSnowflake> queryListBySId(Integer sId) {
        query.clear();
        query.eq("s_id", sId);
        return staticSnowflakeMapper.selectList(query);
    }

    public void update(Long fileId, Integer sId) {
        query.clear();
        query.eq("sf_id", fileId);
        StaticSnowflake staticSnowflake = staticSnowflakeMapper.selectOne(query);
        if (staticSnowflake == null) {
            staticSnowflake = StaticSnowflake.builder().sId(sId).sfId(fileId).build();
            staticSnowflakeMapper.insert(staticSnowflake);
        } else {
            staticSnowflake.setSId(sId);
            staticSnowflakeMapper.updateById(staticSnowflake);
        }
    }
}