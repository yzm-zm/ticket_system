package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.Order;

/**
 * 订单服务接口
 */
public interface OrderService extends IService<Order> {
    /**
     * 分页查询订单
     */
    Page<Order> queryPage(Long current, Long size, Order order);

    /**
     * 创建订单
     */
    Order createOrder(Long userId, Long trainId, java.util.List<com.ticketsystem.dto.OrderDetailDTO> passengers);

    /**
     * 支付订单
     */
    boolean payOrder(Long orderId, String payType);

    /**
     * 退票
     */
    boolean refundOrder(Long orderId);

    /**
     * 改签
     */
    boolean changeOrder(Long orderId, Long newTrainId);

    /**
     * 获取电子票信息
     */
    com.ticketsystem.dto.TicketDTO getTicketInfo(Long orderId);
}

