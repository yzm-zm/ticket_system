package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.Station;

/**
 * 车站服务接口
 */
public interface StationService extends IService<Station> {
    /**
     * 分页查询车站
     */
    Page<Station> queryPage(Long current, Long size, Station station);
}

