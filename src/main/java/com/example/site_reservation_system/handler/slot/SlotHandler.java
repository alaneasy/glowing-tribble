package com.example.site_reservation_system.handler.slot;

import com.example.site_reservation_system.model.slot.Slot;
import com.example.site_reservation_system.service.slot.SlotService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*
 * Created By Alan on 2022/1/11
 * */
@Api("时间段")
@RestController
@RequestMapping("/slot")
public class SlotHandler {

    @Autowired
    private SlotService slotService;

    /*
    *    private Integer id; : null
        private Integer count; : null 第几批的数据
        private Integer day; : 周几
        private String start; 开始时间 格式: {hour:minute} {12:30}
        private String end; 结束时间 如上
        private Integer status; 状态 1:可用 0:不可用
        private BigDecimal payment; 价格
    * */

    @ApiOperation("添加")
    @PostMapping
    @Transactional
    public Result add(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        List<Slot> itemList = mapper.convertValue(params.get("itemList"), new TypeReference<>() {
        });
        itemList.forEach(e -> e.setCount(slotService.queryMaxCount(e.getFId()) + 1));
        itemList.forEach(e -> slotService.insert(e));
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   id : 主键
     * */
    @ApiOperation("单个查询")
    @GetMapping
    public Result query(Integer id) {
        return ResultFactory.buildSuccessResult().put("slot", slotService.queryById(id));
    }

    /*
     *   查询最近的一周的信息
     * */
    @ApiOperation("列表查询")
    @GetMapping("/list")
    public Result queryList(Integer fId) {
        return ResultFactory.buildSuccessResult().put("slotList", slotService.queryListByCountAndFId(slotService.queryMaxCount(fId), fId));
    }

    /*
     *   把一周的信息删除
     * */
    @ApiOperation("删除")
    @DeleteMapping
    @Transactional
    public Result deleteByCount(Integer count) {
        slotService.queryListByCount(count).forEach(e -> slotService.deleteById(e.getId()));
        return ResultFactory.buildSuccessResult();
    }
}