package com.example.site_reservation_system.model.visitor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName("visitor")
public class Visitor implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;
}