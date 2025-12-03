package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.entity.OrderDetail;
import com.ticketsystem.mapper.OrderDetailMapper;
import com.ticketsystem.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单详情服务实现类
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Override
    public Page<OrderDetail> queryPage(Long current, Long size, OrderDetail orderDetail) {
        Page<OrderDetail> page = new Page<>(current, size);
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        if (orderDetail != null) {
            if (orderDetail.getOrderId() != null) {
                wrapper.eq(OrderDetail::getOrderId, orderDetail.getOrderId());
            }
            if (orderDetail.getStatus() != null) {
                wrapper.eq(OrderDetail::getStatus, orderDetail.getStatus());
            }
        }
        wrapper.orderByDesc(OrderDetail::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<OrderDetail> getByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        return this.list(wrapper);
    }
}

