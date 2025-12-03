package com.ticketsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticketsystem.entity.SeatType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 座位类型Mapper接口
 */
@Mapper
public interface SeatTypeMapper extends BaseMapper<SeatType> {
}

