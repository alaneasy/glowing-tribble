package com.example.site_reservation_system.model.field.order;

import com.example.site_reservation_system.model.minio.MinioFile;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * Created By Alan on 2022/2/14
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderV0 implements Serializable {
    private String fieldName;
    private String date;
    private String start;
    private String end;
    private Order base;
    private String phone;
    private MinioFile image;
    private BigDecimal payment;
}