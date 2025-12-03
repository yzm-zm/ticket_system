package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.entity.TrainSeat;
import com.ticketsystem.mapper.TrainSeatMapper;
import com.ticketsystem.service.TrainSeatService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车次座位服务实现类
 */
@Service
public class TrainSeatServiceImpl extends ServiceImpl<TrainSeatMapper, TrainSeat> implements TrainSeatService {

    @Override
    public Page<TrainSeat> queryPage(Long current, Long size, TrainSeat trainSeat) {
        Page<TrainSeat> page = new Page<>(current, size);
        LambdaQueryWrapper<TrainSeat> wrapper = new LambdaQueryWrapper<>();
        if (trainSeat != null) {
            if (trainSeat.getTrainId() != null) {
                wrapper.eq(TrainSeat::getTrainId, trainSeat.getTrainId());
            }
            if (trainSeat.getSeatTypeId() != null) {
                wrapper.eq(TrainSeat::getSeatTypeId, trainSeat.getSeatTypeId());
            }
            if (trainSeat.getStatus() != null) {
                wrapper.eq(TrainSeat::getStatus, trainSeat.getStatus());
            }
        }
        wrapper.orderByDesc(TrainSeat::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<TrainSeat> getByTrainId(Long trainId) {
        LambdaQueryWrapper<TrainSeat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainSeat::getTrainId, trainId);
        wrapper.eq(TrainSeat::getStatus, 1);
        return this.list(wrapper);
    }
}

