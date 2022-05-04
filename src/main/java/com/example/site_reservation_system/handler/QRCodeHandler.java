package com.example.site_reservation_system.handler;

import com.example.site_reservation_system.mapper.GlobalResourceMapper;
import com.example.site_reservation_system.model.GlobalResource;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/*
 * Created By Alan on 2022/3/2
 * */
@RestController
@RequestMapping("/qrcode")
public class QRCodeHandler {

    @Autowired
    private StaticHandler staticHandler;
    @Autowired
    private GlobalResourceMapper globalResourceMapper;

    @Transactional
    @PostMapping
    public Result add(MultipartFile file) {
        globalResourceMapper.updateById(GlobalResource.builder().itemKey("qrcode").itemValue(staticHandler.generate(file).data.get("fileId").toString()).build());
        return ResultFactory.buildSuccessResult();
    }

    @GetMapping
    public Result query(){
        return ResultFactory.buildSuccessResult().put("qrcode", staticHandler.query(Long.parseLong(globalResourceMapper.selectById("qrcode").getItemValue())));
    }
}