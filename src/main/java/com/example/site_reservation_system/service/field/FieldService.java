package com.example.site_reservation_system.service.field;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.site_reservation_system.handler.StaticHandler;
import com.example.site_reservation_system.mapper.field.FieldMapper;
import com.example.site_reservation_system.model.field.Field;
import com.example.site_reservation_system.model.field.FieldV0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Created By Alan on 2022/1/10
 * */
@Service
public class FieldService {

    static final QueryWrapper<Field> queryWrapper = new QueryWrapper<>();

    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private StaticHandler staticHandler;

    private FieldV0 extended(Field item) {
        return FieldV0.builder().base(item).url(staticHandler.query(staticHandler.queryList(item.getStaticId()).get(0)).getUrl()).build();
    }

    private IPage<FieldV0> extended(IPage<Field> item) {
        IPage<FieldV0> result = new Page<>(item.getPages(), item.getSize());
        result.setCurrent(item.getCurrent());
        result.setTotal(item.getTotal());
        result.setRecords(item.getRecords().stream().map(this::extended).collect(Collectors.toList()));
        return result;
    }

    public Field queryById(Serializable id) {
        return fieldMapper.selectById(id);
    }

    public Integer insert(Field item) {
        return fieldMapper.insert(item);
    }

    public Integer updateById(Field item) {
        return fieldMapper.updateById(item);
    }

    public Integer deleteById(Serializable id) {
        return fieldMapper.deleteById(id);
    }

    public List<Field> queryAll() {
        queryWrapper.clear();
        queryWrapper.isNotNull("id");
        return fieldMapper.selectList(queryWrapper);
    }

    public IPage<Field> queryAll(Integer page, Integer size) {
        queryWrapper.clear();
        queryWrapper.isNotNull("id");
        return fieldMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    public IPage<FieldV0> queryListByValid(Integer page, Integer size, String key) {
        queryWrapper.clear();
        queryWrapper.isNotNull("id").eq("status", 1);
        if (key != null) {
            queryWrapper.like("value", key);
        }
        return extended(fieldMapper.selectPage(new Page<>(page, size), queryWrapper));
    }
}