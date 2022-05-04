package com.example.site_reservation_system.service.slot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.site_reservation_system.mapper.slot.SlotMapper;
import com.example.site_reservation_system.model.slot.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Created By Alan on 2022/1/10
 * */
@Service
public class SlotService {

    static final QueryWrapper<Slot> queryWrapper = new QueryWrapper<>();

    @Autowired
    private SlotMapper slotMapper;

    public Slot queryById(Serializable id) {
        return slotMapper.selectById(id);
    }

    public Integer insert(Slot item) {
        return slotMapper.insert(item);
    }

    public Integer updateById(Slot item) {
        return slotMapper.updateById(item);
    }

    public Integer deleteById(Serializable id) {
        return slotMapper.deleteById(id);
    }

    public Integer deleteAll() {
        queryWrapper.clear();
        queryWrapper.isNotNull("id");
        return slotMapper.delete(queryWrapper);
    }

    public List<Slot> queryAll() {
        queryWrapper.clear();
        queryWrapper.isNotNull("id");
        return slotMapper.selectList(queryWrapper);
    }

    public Integer queryMaxCount(Integer fId) {
        queryWrapper.clear();
        queryWrapper.eq("f_id", fId);
        var result = slotMapper.selectList(queryWrapper);
        if(result == null){
            return 0;
        }
        AtomicInteger temp = new AtomicInteger(1);
        result.forEach(e -> temp.set(Math.max(temp.get(), e.getCount())));
        return temp.get();
    }

    public List<Slot> queryListByCountAndFId(Integer count, Integer fId) {
        queryWrapper.clear();
        queryWrapper.eq("count", count).eq("f_id", fId);
        return slotMapper.selectList(queryWrapper);
    }

    public IPage<Slot> queryListByCount(Integer page, Integer size, Integer count) {
        queryWrapper.clear();
        queryWrapper.eq("count", count);
        return slotMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    public List<Slot> queryListByCount(Integer count) {
        queryWrapper.clear();
        queryWrapper.eq("count", count);
        return slotMapper.selectList(queryWrapper);
    }
}