package com.ticketsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticketsystem.entity.Station;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车站Mapper接口
 */
@Mapper
public interface StationMapper extends BaseMapper<Station> {
}

