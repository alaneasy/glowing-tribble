package com.example.site_reservation_system.model.slot.record;

import lombok.*;

import java.math.BigDecimal;

/*
 * Created By Alan on 2022/2/12
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecordV0 {
    private Integer day;
    private String start;
    private String end;
    private BigDecimal payment;
    private Record base;
}