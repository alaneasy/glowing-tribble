package com.example.site_reservation_system.service.field.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.site_reservation_system.handler.StaticHandler;
import com.example.site_reservation_system.mapper.field.FieldMapper;
import com.example.site_reservation_system.mapper.field.order.OrderMapper;
import com.example.site_reservation_system.mapper.slot.SlotMapper;
import com.example.site_reservation_system.model.field.Field;
import com.example.site_reservation_system.model.field.order.Order;
import com.example.site_reservation_system.model.field.order.OrderV0;
import com.example.site_reservation_system.model.slot.record.RecordV0;
import com.example.site_reservation_system.service.slot.record.SlotRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Created By Alan on 2022/1/10
 * */
@Service
public class OrderService {

    static final QueryWrapper<Order> queryWapper = new QueryWrapper<>();

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SlotRecordService slotRecordService;
    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private StaticHandler staticHandler;
    @Autowired
    private SlotMapper slotMapper;

    private OrderV0 extended(Order item) {
        RecordV0 e = slotRecordService.queryById(item.getSrId());
        Field field = fieldMapper.selectById(item.getFId());
        return OrderV0.builder().payment(slotRecordService.queryById(item.getSrId()).getPayment()).phone(field.getPhone()).image(staticHandler.query(staticHandler.queryList(field.getStaticId()).get(0))).base(item).start(e.getStart()).end(e.getEnd()).date(e.getBase().getDate().toLocalDate().toString()).fieldName(field.getValue()).build();
    }

    private IPage<OrderV0> extended(IPage<Order> item) {
        IPage<OrderV0> result = new Page<>(item.getPages(), item.getSize());
        result.setTotal(item.getTotal());
        result.setCurrent(item.getCurrent());
        result.setRecords(item.getRecords().stream().map(this::extended).collect(Collectors.toList()));
        return result;
    }

    private List<OrderV0> extended(List<Order> item) {
        return item.stream().map(this::extended).collect(Collectors.toList());
    }

    public OrderV0 queryById(Serializable id) {
        return extended(orderMapper.selectById(id));
    }

    public Integer insert(Order item) {
        return orderMapper.insert(item);
    }

    public Integer updateById(Order item) {
        return orderMapper.updateById(item);
    }

    public Integer deleteById(Integer id) {
        return orderMapper.deleteById(id);
    }

    public IPage<OrderV0> queryListByVId(String vId, Integer page, Integer size) {
        return extended(orderMapper.queryPageByVId(new Page<>(page, size), vId));
    }

    public IPage<OrderV0> queryListByFId(Integer fId, Integer page, Integer size) {
        return extended(orderMapper.queryPageByFId(new Page<>(page, size), fId));
    }

    public IPage<OrderV0> queryAll(Integer page, Integer size) {
        return extended(orderMapper.queryPage(new Page<>(page, size)));
    }

    public IPage<OrderV0> queryFind(Integer page, Integer size, String key, Integer status, String vId) {
        return extended(orderMapper.queryFind(new Page<>(page, size), "%" + key + "%", status, vId));
    }

    public IPage<OrderV0> queryListByStatus(Integer page, Integer size, Integer status) {
        return extended(orderMapper.queryPageByStatus(new Page<>(page, size), status));
    }

    public List<OrderV0> statistics(Integer year, Integer fId) {
        return extended(orderMapper.statistics(year, fId));
    }
}