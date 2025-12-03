package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.dto.ChangeOrderDTO;
import com.ticketsystem.dto.CreateOrderDTO;
import com.ticketsystem.dto.PayOrderDTO;
import com.ticketsystem.entity.Order;
import com.ticketsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询订单
     */
    @PostMapping("/query_page")
    public Result<PageResult<Order>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) Order order) {
        Page<Order> page = orderService.queryPage(current, size, order);
        PageResult<Order> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除订单
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        orderService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存订单（新增或更新）
     */
    @PostMapping("/save")
    public Result<Order> save(@RequestBody Order order) {
        orderService.saveOrUpdate(order);
        return Result.success(order);
    }

    /**
     * 根据ID查询订单
     */
    @GetMapping("/select_by_id")
    public Result<Order> selectById(@RequestParam Long id) {
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestParam Long userId, @RequestBody CreateOrderDTO createOrderDTO) {
        try {
            Order order = orderService.createOrder(userId, createOrderDTO.getTrainId(), createOrderDTO.getPassengers());
            return Result.success(order);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 支付订单
     */
    @PostMapping("/pay")
    public Result<Void> payOrder(@RequestBody PayOrderDTO payOrderDTO) {
        boolean success = orderService.payOrder(payOrderDTO.getOrderId(), payOrderDTO.getPayType());
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("支付失败");
        }
    }

    /**
     * 退票
     */
    @PostMapping("/refund")
    public Result<Void> refundOrder(@RequestParam Long orderId) {
        boolean success = orderService.refundOrder(orderId);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("退票失败");
        }
    }

    /**
     * 改签
     */
    @PostMapping("/change")
    public Result<Void> changeOrder(@RequestBody ChangeOrderDTO changeOrderDTO) {
        boolean success = orderService.changeOrder(changeOrderDTO.getOrderId(), changeOrderDTO.getNewTrainId());
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("改签失败");
        }
    }

    /**
     * 下载电子票
     */
    @GetMapping("/download_ticket")
    public Result<com.ticketsystem.dto.TicketDTO> downloadTicket(@RequestParam Long orderId) {
        Order order = orderService.getById(orderId);
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (order.getOrderStatus() != 1) {
            return Result.error("只有已支付的订单才能下载电子票");
        }
        com.ticketsystem.dto.TicketDTO ticket = orderService.getTicketInfo(orderId);
        return Result.success(ticket);
    }
}

