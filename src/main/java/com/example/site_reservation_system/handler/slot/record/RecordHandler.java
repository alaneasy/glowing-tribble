package com.example.site_reservation_system.handler.slot.record;

import com.example.site_reservation_system.model.slot.record.Record;
import com.example.site_reservation_system.service.slot.record.SlotRecordService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Map;

/*
 * Created By Alan on 2022/1/11
 * */

@Api("时间段记录")
@RestController("slotRecordHandler")
@RequestMapping("/slot/record")
public class RecordHandler {

    @Autowired
    private SlotRecordService recordService;

    /*
     *   数据由定时器自动生成 没有添加的入口
     * */

    /*
     *   id : 主键
     * */
    @ApiOperation("当个查询")
    @GetMapping
    public Result query(Serializable id) {
        return ResultFactory.buildSuccessResult().put("record", recordService.queryById(id));
    }

    /*
     *   f_id : 场地id
     * */
    @ApiOperation("列表查询")
    @GetMapping("/list")
    public Result queryListByFId(Integer fId) {
        return ResultFactory.buildSuccessResult().put("record", recordService.queryListByFId(fId));
    }

    @GetMapping("/date")
    public Result queryDate(Integer fId) {
        return ResultFactory.buildSuccessResult().put("dateList", recordService.queryDate(Date.valueOf(ZonedDateTime.now().toLocalDate()), fId));
    }

    @GetMapping("/list_date")
    public Result queryByDateAndFId(Integer fId, String date) throws ParseException {
        return ResultFactory.buildSuccessResult().put("recordList", recordService.queryByDateAndFId(fId, new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(date).getTime())));
    }

    /*
     *   需要对象全部的属性
     * */
    @ApiOperation("更新")
    @PutMapping
    public Result update(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        Record item = mapper.convertValue(params.get("item"), new TypeReference<>() {
        });
        recordService.updateById(item);
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   id : 主键
     * */
    @ApiOperation("删除")
    @DeleteMapping
    public Result delete(Integer id) {
        recordService.deleteById(id);
        return ResultFactory.buildSuccessResult();
    }
}