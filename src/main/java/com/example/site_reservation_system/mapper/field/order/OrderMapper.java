package com.example.site_reservation_system.mapper.field.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import com.example.site_reservation_system.model.field.order.Order;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
 * Created By Alan on 2022/1/10
 * */

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from field_order as fo, slot_record as sr where fo.sr_id = sr.id and fo.v_id = #{vId} and fo.status = #{status} and fo.f_id in (select id from field where value like #{key}) order by sr.date desc")
    IPage<Order> queryFind(Page<Order> page, String key, Integer status, String vId);

    @Select("select * from field_order as fo, slot_record as sr where fo.sr_id = sr.id and fo.status = 2 and fo.f_id = #{fId} and year(sr.date) = #{year} order by sr.date desc")
    List<Order> statistics(Integer year, Integer fId);

    @Select("select * from field_order as fo, slot_record as sr where fo.sr_id = sr.id and fo.v_id = #{vId} order by sr.date desc")
    IPage<Order> queryPageByVId(Page<?> page, String vId);

    @Select("select * from field_order as fo, slot_record as sr where fo.sr_id = sr.id and fo.f_id = #{fId} order by sr.date desc")
    IPage<Order> queryPageByFId(Page<?> page, Integer fId);

    @Select("select * from field_order as fo, slot_record as sr where fo.sr_id = sr.id order by sr.date desc")
    IPage<Order> queryPage(Page<?> page);

    @Select("select * from field_order as fo, slot_record as sr where fo.sr_id = sr.id and fo.status = #{status} order by sr.date desc")
    IPage<Order> queryPageByStatus(Page<?> page, Integer status);
}