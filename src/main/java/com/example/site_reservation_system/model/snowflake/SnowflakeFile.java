package com.example.site_reservation_system.model.snowflake;

import com.example.site_reservation_system.model.minio.MinioFile;
import lombok.*;

/*
 * Created By Alan on 2022/2/11
 * */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SnowflakeFile {
    private String fileId;
    private MinioFile file;
}