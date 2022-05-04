package com.example.site_reservation_system.mapper.slot.record;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.site_reservation_system.model.slot.record.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

/*
 * Created By Alan on 2022/1/10
 * */

@Mapper
public interface SlotRecordMapper extends BaseMapper<Record> {

    @Select("select date from slot_record where f_id = #{fId} and date >= #{date}")
    List<Date> queryDate(Date date, Integer fId);
}