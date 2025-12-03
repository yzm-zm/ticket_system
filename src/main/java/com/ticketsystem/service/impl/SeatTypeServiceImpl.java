package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.entity.SeatType;
import com.ticketsystem.mapper.SeatTypeMapper;
import com.ticketsystem.service.SeatTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 座位类型服务实现类
 */
@Service
public class SeatTypeServiceImpl extends ServiceImpl<SeatTypeMapper, SeatType> implements SeatTypeService {

    @Override
    public Page<SeatType> queryPage(Long current, Long size, SeatType seatType) {
        Page<SeatType> page = new Page<>(current, size);
        LambdaQueryWrapper<SeatType> wrapper = new LambdaQueryWrapper<>();
        if (seatType != null) {
            if (StringUtils.hasText(seatType.getTypeName())) {
                wrapper.like(SeatType::getTypeName, seatType.getTypeName());
            }
            if (seatType.getStatus() != null) {
                wrapper.eq(SeatType::getStatus, seatType.getStatus());
            }
        }
        wrapper.orderByDesc(SeatType::getCreateTime);
        return this.page(page, wrapper);
    }
}

