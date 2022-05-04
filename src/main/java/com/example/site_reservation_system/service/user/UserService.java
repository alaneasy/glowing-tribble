package com.example.site_reservation_system.service.user;

import com.example.site_reservation_system.mapper.user.UserMapper;
import com.example.site_reservation_system.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/*
 * Created By Alan on 2022/2/16
 * */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Serializable id) {
        return userMapper.selectById(id);
    }
}