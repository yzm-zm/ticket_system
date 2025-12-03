package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.TrainSeat;

/**
 * 车次座位服务接口
 */
public interface TrainSeatService extends IService<TrainSeat> {
    /**
     * 分页查询车次座位
     */
    Page<TrainSeat> queryPage(Long current, Long size, TrainSeat trainSeat);

    /**
     * 根据车次ID查询座位信息
     */
    java.util.List<TrainSeat> getByTrainId(Long trainId);
}

