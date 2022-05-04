package com.example.site_reservation_system.handler;

import com.example.site_reservation_system.model.Football;
import com.example.site_reservation_system.service.FootballService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 * Created By Alan on 2022/3/2
 * */
@RestController
@RequestMapping("/football")
public class FootballHandler {

    @Autowired
    private FootballService footballService;

    @PostMapping
    public Result add(@RequestBody Map<String, Object> params) {
        ObjectMapper om = new ObjectMapper();
        Football item = om.convertValue(params.get("item"), new TypeReference<Football>() {
        });
        footballService.insert(item);
        return ResultFactory.buildSuccessResult();
    }

    @GetMapping("/list")
    public Result queryAll(Integer page, Integer size) {
        return ResultFactory.buildSuccessResult().put("footballList", footballService.queryAll(page, size));
    }

    @PutMapping
    public Result update(@RequestBody Map<String, Object> params) {
        ObjectMapper om = new ObjectMapper();
        Football item = om.convertValue(params.get("item"), new TypeReference<Football>() {
        });
        footballService.updateById(item);
        return ResultFactory.buildSuccessResult();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        footballService.deleteById(id);
        return ResultFactory.buildSuccessResult();
    }
}