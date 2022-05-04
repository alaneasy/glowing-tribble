package com.example.site_reservation_system.model.snowflake;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/*
 * Created By Alan on 2022/2/10
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("snowflake")
public class Snowflake implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
}