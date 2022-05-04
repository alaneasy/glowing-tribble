package com.example.site_reservation_system.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.site_reservation_system.model.user.User;
import org.apache.ibatis.annotations.Mapper;

/*
 * Created By Alan on 2022/2/16
 * */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}