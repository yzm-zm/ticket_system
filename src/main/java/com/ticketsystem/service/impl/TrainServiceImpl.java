package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.entity.Train;
import com.ticketsystem.mapper.TrainMapper;
import com.ticketsystem.service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 车次服务实现类
 */
@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements TrainService {

    @Override
    public Page<Train> queryPage(Long current, Long size, Train train) {
        Page<Train> page = new Page<>(current, size);
        LambdaQueryWrapper<Train> wrapper = new LambdaQueryWrapper<>();
        if (train != null) {
            if (StringUtils.hasText(train.getTrainNo())) {
                wrapper.like(Train::getTrainNo, train.getTrainNo());
            }
            if (train.getDepartureStationId() != null) {
                wrapper.eq(Train::getDepartureStationId, train.getDepartureStationId());
            }
            if (train.getArrivalStationId() != null) {
                wrapper.eq(Train::getArrivalStationId, train.getArrivalStationId());
            }
            if (train.getStatus() != null) {
                wrapper.eq(Train::getStatus, train.getStatus());
            }
        }
        wrapper.orderByDesc(Train::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public Page<Train> queryTrainWithSeats(Long current, Long size, Long departureStationId, Long arrivalStationId, String departureDate) {
        Page<Train> page = new Page<>(current, size);
        LambdaQueryWrapper<Train> wrapper = new LambdaQueryWrapper<>();
        if (departureStationId != null) {
            wrapper.eq(Train::getDepartureStationId, departureStationId);
        }
        if (arrivalStationId != null) {
            wrapper.eq(Train::getArrivalStationId, arrivalStationId);
        }
        if (StringUtils.hasText(departureDate)) {
            try {
                LocalDate date = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDateTime startTime = date.atStartOfDay();
                LocalDateTime endTime = date.plusDays(1).atStartOfDay();
                wrapper.ge(Train::getDepartureTime, startTime);
                wrapper.lt(Train::getDepartureTime, endTime);
            } catch (Exception e) {
                // 日期解析失败，忽略该条件
            }
        }
        wrapper.eq(Train::getStatus, 1);
        wrapper.orderByAsc(Train::getDepartureTime);
        return this.page(page, wrapper);
    }
}

