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
@TableName("global_resource")
public class GlobalResource {
    @TableId(type = IdType.ASSIGN_ID)
    private String itemKey;
    private String itemValue;
}