package com.example.site_reservation_system.model.slot.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

/*
 * Created By Alan on 2022/1/9
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("slot_record")
public class Record implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fId;
    private Integer sId;
    private Integer status;
    private Date date;
}