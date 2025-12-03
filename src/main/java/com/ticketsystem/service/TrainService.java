package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.Train;

/**
 * 车次服务接口
 */
public interface TrainService extends IService<Train> {
    /**
     * 分页查询车次
     */
    Page<Train> queryPage(Long current, Long size, Train train);

    /**
     * 查询车次（包含车站信息和座位信息）
     */
    Page<Train> queryTrainWithSeats(Long current, Long size, Long departureStationId, Long arrivalStationId, String departureDate);
}

