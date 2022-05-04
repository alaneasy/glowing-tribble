package com.example.site_reservation_system.model.slot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * Created By Alan on 2022/1/9
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("slot")
public class Slot implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer count;
    private Integer day;
    private String start;
    private String end;
    private Integer status;
    private BigDecimal payment;
    private Integer fId;
}