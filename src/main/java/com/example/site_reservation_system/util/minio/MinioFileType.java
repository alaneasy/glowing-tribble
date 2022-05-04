package com.example.site_reservation_system.util.minio;

import com.example.site_reservation_system.model.minio.MinioFile;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MinioFileType {
    static Set<String> imageSet = new HashSet<>();
    static Set<String> videoSet = new HashSet<>();

    static {
        imageSet.add("jpg");
        imageSet.add("png");
        imageSet.add("jpeg");
        videoSet.add("mp4");
        videoSet.add("mkv");
        videoSet.add("avi");
        videoSet.add("wmv");
        videoSet.add("mov");
    }

    public static MinioFile.Type judgeType(String fileName) {
        String fileType = fileName.substring(fileName.toLowerCase(Locale.ROOT).indexOf(".") + 1);
        if (imageSet.contains(fileType)) {
            return MinioFile.Type.PICTURE;
        }
        if (videoSet.contains(fileType)) {
            return MinioFile.Type.VIDEO;
        }
        return MinioFile.Type.FILE;
    }
}