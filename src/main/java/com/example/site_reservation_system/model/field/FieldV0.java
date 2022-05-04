package com.example.site_reservation_system.model.field;

import lombok.*;

import java.io.Serializable;

/*
 * Created By Alan on 2022/2/14
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldV0 implements Serializable {
    private String url;
    private Field base;
}