package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.OrderDetail;

/**
 * 订单详情服务接口
 */
public interface OrderDetailService extends IService<OrderDetail> {
    /**
     * 分页查询订单详情
     */
    Page<OrderDetail> queryPage(Long current, Long size, OrderDetail orderDetail);

    /**
     * 根据订单ID查询订单详情列表
     */
    java.util.List<OrderDetail> getByOrderId(Long orderId);
}

