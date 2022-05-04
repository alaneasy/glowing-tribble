package com.example.site_reservation_system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/*
 * Created By Alan on 2022/3/2
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("football")
public class Football {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String phone;
}