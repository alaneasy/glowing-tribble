package com.example.site_reservation_system.mapper.visitor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.site_reservation_system.model.visitor.Visitor;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author alan
 */
@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {
}