package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.entity.Station;
import com.ticketsystem.mapper.StationMapper;
import com.ticketsystem.service.StationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 车站服务实现类
 */
@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements StationService {

    @Override
    public Page<Station> queryPage(Long current, Long size, Station station) {
        Page<Station> page = new Page<>(current, size);
        LambdaQueryWrapper<Station> wrapper = new LambdaQueryWrapper<>();
        if (station != null) {
            if (StringUtils.hasText(station.getStationName())) {
                wrapper.like(Station::getStationName, station.getStationName());
            }
            if (StringUtils.hasText(station.getCity())) {
                wrapper.like(Station::getCity, station.getCity());
            }
            if (station.getStatus() != null) {
                wrapper.eq(Station::getStatus, station.getStatus());
            }
        }
        wrapper.orderByDesc(Station::getCreateTime);
        return this.page(page, wrapper);
    }
}

