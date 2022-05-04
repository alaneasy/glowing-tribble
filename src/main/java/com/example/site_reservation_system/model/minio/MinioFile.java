package com.example.site_reservation_system.model.minio;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MinioFile implements Serializable {
    public enum Type {
        PICTURE, FILE, VIDEO
    }
    private String name;
    private Long size;
    private String url;
    private ZonedDateTime date;
    private Type type;
}