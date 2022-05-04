package com.example.site_reservation_system.model.field;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

/*
 * Created By Alan on 2022/1/9
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("field")
public class Field implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String value;
    private String phone;
    private String position;
    private Integer staticId;
    private Integer status;
    private String description;
    private Double payment;
    private String businessTime;
    private String positionDetail;
}