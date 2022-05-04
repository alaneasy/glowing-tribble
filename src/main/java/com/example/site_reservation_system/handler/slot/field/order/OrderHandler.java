package com.example.site_reservation_system.handler.slot.field.order;

import com.example.site_reservation_system.model.field.order.Order;
import com.example.site_reservation_system.model.field.order.OrderV0;
import com.example.site_reservation_system.model.slot.record.Record;
import com.example.site_reservation_system.service.field.order.OrderService;
import com.example.site_reservation_system.service.slot.record.SlotRecordService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created By Alan on 2022/1/11
 * */
@Api("预定")
@RestController
@RequestMapping("/field/order")
public class OrderHandler {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SlotRecordService slotRecordService;

    /*
    *     private Integer id; : null
        private String vId; : 游客id
        private Integer fId; : 场地Id
        private Integer srId; : 时间段记录id
        private String comment; : 备注
        private Integer status; : 状态 1:可用 0:不可用
    * */

    @ApiOperation("添加")
    @PostMapping
    @Transactional
    public Result add(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        Order item = mapper.convertValue(params.get("item"), new TypeReference<>() {
        });
        item.setVId(SecurityUtils.getSubject().getPrincipal().toString());
        item.setStatus(1);
        Record record = slotRecordService.queryById(item.getSrId()).getBase();
        if (record.getStatus() == 1) {
            return ResultFactory.buildErrorResult("添加失败");
        }
        orderService.insert(item);
        record.setStatus(1);
        slotRecordService.updateById(record);
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   id : 主键
     * */
    @ApiOperation("单个查询")
    @GetMapping
    public Result query(Integer id) {
        return ResultFactory.buildSuccessResult().put("order", orderService.queryById(id));
    }

    /*
     *   f_id : 场地id
     * */
    @ApiOperation("列表查询")
    @GetMapping("/list_f")
    public Result queryListByFId(Integer page, Integer size, Integer f_id) {
        return ResultFactory.buildSuccessResult().put("orderList", orderService.queryListByFId(f_id, page, size));
    }

    @GetMapping("/find")
    public Result queryFind(Integer page, Integer size, String key, Integer status) {
        return ResultFactory.buildSuccessResult().put("orderList", orderService.queryFind(page, size, key, status, SecurityUtils.getSubject().getPrincipal().toString()));
    }

    /*
     *   v_id : 游客id
     * */
    @ApiOperation("列表查询")
    @GetMapping("/list_v")
    public Result queryListByVId(Integer page, Integer size, String v_id) {
        return ResultFactory.buildSuccessResult().put("orderList", orderService.queryListByVId(v_id, page, size));
    }

    @ApiOperation("列表查询")
    @GetMapping("/list")
    public Result queryAll(Integer page, Integer size) {
        return ResultFactory.buildSuccessResult().put("orderList", orderService.queryAll(page, size));
    }

    @ApiOperation("列表查询")
    @GetMapping("/list_s")
    public Result queryListByStatus(Integer page, Integer size, Integer status) {
        return ResultFactory.buildSuccessResult().put("orderList", orderService.queryListByStatus(page, size, status));
    }

    @ApiOperation("取消")
    @PutMapping("/cancel")
    @Transactional
    public Result cancel(@RequestBody Map<String, Object> params) {
        ObjectMapper om = new ObjectMapper();
        Integer id = om.convertValue(params.get("id"), new TypeReference<>() {
        });
        Order order = orderService.queryById(id).getBase();
        if (order.getStatus() == 0) {
            return ResultFactory.buildErrorResult("取消失败");
        }
        order.setStatus(0);
        orderService.updateById(order);
        Record record = slotRecordService.queryById(order.getSrId()).getBase();
        record.setStatus(0);
        slotRecordService.updateById(record);
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   需要传入对象所有信息
     * 1 预订 2 付款 3 完成 4退款 0 取消
     * */
    @ApiOperation("更新")
    @PutMapping
    @Transactional
    public Result update(@RequestBody Map<String, Object> params) {
        ObjectMapper mapper = new ObjectMapper();
        Order item = mapper.convertValue(params.get("item"), new TypeReference<>() {
        });
        orderService.updateById(item);
        if (item.getStatus() == 4) {
            Record record = slotRecordService.queryById(item.getSrId()).getBase();
            record.setStatus(0);
            slotRecordService.updateById(record);
        }
        return ResultFactory.buildSuccessResult();
    }

    /*
     *   id : 主键
     * */
    @ApiOperation("删除")
    @DeleteMapping
    public Result delete(Integer id) {
        orderService.deleteById(id);
        return ResultFactory.buildSuccessResult();
    }

    @GetMapping("/statistics")
    public Result statistics(Integer year, Integer fId) {
        List<OrderV0> statistics = orderService.statistics(year, fId);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 12; ++i) {
            map.put(i, 0);
        }
        statistics.forEach(e -> map.put(Integer.parseInt(e.getDate().split("-")[1]), e.getPayment().intValue() + map.get(Integer.parseInt(e.getDate().split("-")[1]))));
        return ResultFactory.buildSuccessResult().put("statistics", map);
    }
}