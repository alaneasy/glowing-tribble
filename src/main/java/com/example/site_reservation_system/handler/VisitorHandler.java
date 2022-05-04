package com.example.site_reservation_system.handler;

import com.example.site_reservation_system.service.visitor.VisitorService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created By Alan on 2022/2/14
 * */
@RestController
@RequestMapping("/visitor")
public class VisitorHandler {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/list")
    public Result queryAll(Integer page, Integer size) {
        return ResultFactory.buildSuccessResult().put("visitorList", visitorService.queryAll(page, size));
    }
}