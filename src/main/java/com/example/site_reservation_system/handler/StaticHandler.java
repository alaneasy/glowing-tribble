package com.example.site_reservation_system.handler;

import com.example.site_reservation_system.model.minio.MinioFile;
import com.example.site_reservation_system.model.snowflake.SnowflakeFile;
import com.example.site_reservation_system.model.snowflake.StaticResource;
import com.example.site_reservation_system.model.snowflake.StaticSnowflake;
import com.example.site_reservation_system.service.minio.MinioService;
import com.example.site_reservation_system.service.snowflake.StaticResourceService;
import com.example.site_reservation_system.service.snowflake.StaticSnowflakeService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Created By Alan on 2022/2/10
 * */
@RestController
@RequestMapping("/static")
public class StaticHandler {

    @Autowired
    private StaticResourceService staticResourceService;
    @Autowired
    private MinioService minioService;
    @Autowired
    private StaticSnowflakeService staticSnowflakeService;

    @PostMapping
    @Transactional
    public Result add(@RequestBody Map<String, Object> params) {
        ObjectMapper om = new ObjectMapper();
        String fileIdList = om.convertValue(params.get("fileIdList"), new TypeReference<String>() {
        });
        StaticResource staticResource = StaticResource.builder().dateTime(ZonedDateTime.now()).build();
        staticResourceService.insert(staticResource);
        if (fileIdList != null) {
            Arrays.stream(fileIdList.split("&")).filter(e -> !e.isEmpty()).forEach(e -> staticSnowflakeService.update(Long.parseLong(e), staticResource.getId()));
        }
        return ResultFactory.buildSuccessResult().put("staticId", staticResource.getId());
    }

    @PostMapping("/generate")
    public Result generate(MultipartFile file) {
        return ResultFactory.buildSuccessResult().put("fileId", minioService.upload(file).toString());
    }

    @GetMapping
    public Result query(Integer id) {
        List<SnowflakeFile> list = new ArrayList<>();
        staticSnowflakeService.queryListBySId(id).forEach(e -> list.add(SnowflakeFile.builder().fileId(e.getSfId().toString()).build()));
        list.forEach(e -> e.setFile(query(Long.parseLong(e.getFileId()))));
        return ResultFactory.buildSuccessResult().put("fileList", list);
    }

    public List<Long> queryList(Integer id) {
        if (id == null) {
            return null;
        }
        return staticSnowflakeService.queryListBySId(id).stream().map(StaticSnowflake::getSfId).collect(Collectors.toList());
    }

    public MinioFile query(Long id) {
        return minioService.getUrl(id.toString());
    }
}