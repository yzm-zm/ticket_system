package com.ticketsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticketsystem.entity.TrainSeat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车次座位Mapper接口
 */
@Mapper
public interface TrainSeatMapper extends BaseMapper<TrainSeat> {
}

