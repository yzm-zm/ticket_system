package com.ticketsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticketsystem.entity.Train;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车次Mapper接口
 */
@Mapper
public interface TrainMapper extends BaseMapper<Train> {
}

