package com.example.site_reservation_system.service.minio;

import com.example.site_reservation_system.model.minio.MinioFile;
import com.example.site_reservation_system.model.snowflake.Snowflake;
import com.example.site_reservation_system.model.snowflake.StaticResource;
import com.example.site_reservation_system.model.snowflake.StaticSnowflake;
import com.example.site_reservation_system.service.snowflake.SnowflakeService;
import com.example.site_reservation_system.service.snowflake.StaticSnowflakeService;
import com.example.site_reservation_system.util.minio.MinioFileType;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MinioService {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private StaticSnowflakeService staticSnowflakeService;
    @Autowired
    private SnowflakeService snowflakeService;

    public void upload(final String bucket, final MultipartFile[] files) {
        if (files == null) {
            this.makeBucket(bucket);
        } else {
            this.makeBucket(bucket);
            Arrays.stream(files).forEach(file -> {
                try {
                    this.uploadObject(file.getInputStream(), bucket, file.getOriginalFilename(), file.getContentType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void upload(final String bucket, final MultipartFile file) {
        if (file == null) {
            this.makeBucket(bucket);
        } else {
            this.makeBucket(bucket);
            try {
                this.uploadObject(file.getInputStream(), bucket, file.getOriginalFilename(), file.getContentType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取列表
    public List<MinioFile> listObjects(final String bucketName) {
        if (bucketName == null) {
            return null;
        }
        this.makeBucket(bucketName);
        List<MinioFile> list = new ArrayList<>();
        try {
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder().bucket(bucketName).build();
            Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);
            for (Result<Item> result : results) {
                Item item = result.get();
                log.info(item.lastModified() + ", " + item.size() + ", " + item.objectName());
                MinioFile minioFile = new MinioFile();
                minioFile.setName(item.objectName());
                minioFile.setDate(item.lastModified());
                minioFile.setSize(item.size());
                minioFile.setUrl(getObjectUrl(bucketName, item.objectName()));
                minioFile.setType(MinioFileType.judgeType(item.objectName()));
                list.add(minioFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //上传
    private void uploadObject(InputStream is, String bucketName, String fileName, String contentType) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(fileName).contentType(contentType).stream(is, is.available(), -1).build();
            minioClient.putObject(putObjectArgs);
            is.close();
        } catch (Exception ignored) {
        }
    }

    //获取minio中地址
    private String getObjectUrl(String id, String objectName) {
        try {
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(id).object(objectName).expiry(60, TimeUnit.MINUTES).build();
            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception ignored) {
        }
        return "";
    }

    private void makeBucket(String bucketName) {
        if (bucketName == null) return;
        try {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                minioClient.makeBucket(makeBucketArgs);
            }
        } catch (Exception ignored) {
        }
    }

    public void upload(StaticResource staticResource, MultipartFile[] fileList) {
        if (fileList == null) {
            return;
        }
        Arrays.stream(fileList).forEach(e -> upload(staticResource, e));
    }

    public void upload(StaticResource staticResource, MultipartFile file) {
        Snowflake snowflake = Snowflake.builder().build();
        snowflakeService.insert(snowflake);
        upload(snowflake.getId().toString(), file);
        StaticSnowflake staticSnowflake = StaticSnowflake.builder().sId(staticResource.getId()).sfId(snowflake.getId()).build();
        staticSnowflakeService.insert(staticSnowflake);
    }

    public MinioFile getUrl(String bucket) {
        return listObjects(bucket).get(0);
    }

    public void upload(Integer sId, MultipartFile file) {
        Snowflake snowflake = Snowflake.builder().build();
        snowflakeService.insert(snowflake);
        upload(snowflake.getId().toString(), file);
        StaticSnowflake staticSnowflake = StaticSnowflake.builder().sId(sId).sfId(snowflake.getId()).build();
        staticSnowflakeService.insert(staticSnowflake);
    }

    public Long upload(MultipartFile file) {
        Snowflake snowflake = Snowflake.builder().build();
        snowflakeService.insert(snowflake);
        upload(snowflake.getId().toString(), file);
        return snowflake.getId();
    }
}