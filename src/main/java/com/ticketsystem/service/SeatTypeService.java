package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.SeatType;

/**
 * 座位类型服务接口
 */
public interface SeatTypeService extends IService<SeatType> {
    /**
     * 分页查询座位类型
     */
    Page<SeatType> queryPage(Long current, Long size, SeatType seatType);
}

