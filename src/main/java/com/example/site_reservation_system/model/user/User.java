package com.example.site_reservation_system.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/*
 * Created By Alan on 2022/2/16
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.INPUT)
    private String id;
    private String avatarUrl;
    private String nickName;
    private Integer gender;
    private String token;
    private String openId;
    private Integer cId;
    private String otherCode;
    private Integer other;
    private Integer height;
    private Integer status;
}