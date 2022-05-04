package com.example.site_reservation_system.service.slot.record;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.site_reservation_system.mapper.slot.record.SlotRecordMapper;
import com.example.site_reservation_system.model.slot.Slot;
import com.example.site_reservation_system.model.slot.record.Record;
import com.example.site_reservation_system.model.slot.record.RecordV0;
import com.example.site_reservation_system.service.slot.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/*
 * Created By Alan on 2022/1/10
 * */
@Service
public class SlotRecordService {

    static final QueryWrapper<Record> queryWapper = new QueryWrapper<>();
    static Map<Integer, String> map = new HashMap<>();

    static {
        map.put(1, "星期一");
        map.put(2, "星期二");
        map.put(3, "星期三");
        map.put(4, "星期四");
        map.put(5, "星期五");
        map.put(6, "星期六");
        map.put(0, "星期日");
    }

    @Autowired
    private SlotRecordMapper recordMapper;
    @Autowired
    private SlotService slotService;


    private List<RecordV0> extended(List<Record> item) {
        return item.stream().map(this::extended).collect(Collectors.toList());
    }

    private IPage<RecordV0> extended(IPage<Record> item) {
        Page<RecordV0> page = new Page<>(item.getPages(), item.getSize());
        page.setRecords(extended(item.getRecords()));
        page.setTotal(item.getTotal());
        page.setCurrent(item.getCurrent());
        return page;
    }

    private RecordV0 extended(Record item) {
        Slot slot = slotService.queryById(item.getSId());
        return RecordV0.builder().base(item).day(item.getDate().toLocalDate().getDayOfWeek().getValue()).end(slot.getEnd()).start(slot.getStart()).payment(slot.getPayment()).build();
    }

    public RecordV0 queryById(Serializable id) {
        return extended(recordMapper.selectById(id));
    }

    public IPage<RecordV0> queryListBySId(Serializable sId, Integer page, Integer size) {
        queryWapper.clear();
        queryWapper.eq("s_id", sId);
        return extended(recordMapper.selectPage(new Page<>(page, size), queryWapper));
    }

    public IPage<RecordV0> queryListByFId(Serializable fId, Integer page, Integer size) {
        queryWapper.clear();
        queryWapper.eq("f_id", fId);
        return extended(recordMapper.selectPage(new Page<>(page, size), queryWapper));
    }

    public List<RecordV0> queryListByFId(Serializable fId) {
        queryWapper.clear();
        queryWapper.eq("f_id", fId);
        return extended(recordMapper.selectList(queryWapper));
    }

    public Integer insert(Record item) {
        queryWapper.clear();
        queryWapper.eq("date", item.getDate()).eq("f_id", item.getFId()).eq("s_id", item.getSId());
        Long count = recordMapper.selectCount(queryWapper);
        if (count == 0) {
            queryWapper.clear();
            queryWapper.eq("date", item.getDate()).eq("f_id", item.getFId());
            boolean match = recordMapper.selectList(queryWapper).stream().anyMatch(e -> !slotService.queryById(e.getSId()).getCount().equals(slotService.queryMaxCount(e.getFId())));
            if (!match) {
                return recordMapper.insert(item);
            }
        }
        return 0;
    }

    public Integer updateById(Record item) {
        return recordMapper.updateById(item);
    }

    public Integer deleteById(Serializable id) {
        return recordMapper.deleteById(id);
    }

    public List<com.example.site_reservation_system.model.Date> queryDate(Date date, Integer fId) {
        return new TreeSet<>(recordMapper.queryDate(date, fId)).stream().map(e -> com.example.site_reservation_system.model.Date.builder().date(e.toLocalDate().toString()).day(map.get(e.getDay())).build()).collect(Collectors.toList());
    }

    public List<RecordV0> queryByDateAndFId(Integer fId, Date date) {
        queryWapper.clear();
        queryWapper.eq("date", date);
        queryWapper.eq("f_id", fId);
        return extended(recordMapper.selectList(queryWapper));
    }
}