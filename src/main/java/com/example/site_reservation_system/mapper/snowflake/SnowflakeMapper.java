package com.example.site_reservation_system.mapper.snowflake;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.site_reservation_system.model.snowflake.Snowflake;
import org.apache.ibatis.annotations.Mapper;

/*
 * Created By Alan on 2022/2/10
 * */
@Mapper
public interface SnowflakeMapper extends BaseMapper<Snowflake> {
}