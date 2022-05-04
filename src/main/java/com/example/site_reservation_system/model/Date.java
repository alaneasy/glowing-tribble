package com.example.site_reservation_system.model;

import lombok.Builder;
import lombok.Data;

/*
 * Created By Alan on 2022/2/13
 * */
@Data
@Builder
public class Date {
    private String date;
    private String day;
}