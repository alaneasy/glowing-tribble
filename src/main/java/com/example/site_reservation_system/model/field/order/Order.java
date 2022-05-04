package com.example.site_reservation_system.model.field.order;

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
@TableName("field_order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String vId;
    private Integer fId;
    private Integer srId;
    private String comment;
    private Integer status;
    private Integer number;
    private String telephone;
    private String teamName;
}