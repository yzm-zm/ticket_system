package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.entity.OrderDetail;
import com.ticketsystem.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单详情控制器
 */
@RestController
@RequestMapping("/api/order_detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 分页查询订单详情
     */
    @PostMapping("/query_page")
    public Result<PageResult<OrderDetail>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) OrderDetail orderDetail) {
        Page<OrderDetail> page = orderDetailService.queryPage(current, size, orderDetail);
        PageResult<OrderDetail> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除订单详情
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        orderDetailService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存订单详情（新增或更新）
     */
    @PostMapping("/save")
    public Result<OrderDetail> save(@RequestBody OrderDetail orderDetail) {
        orderDetailService.saveOrUpdate(orderDetail);
        return Result.success(orderDetail);
    }

    /**
     * 根据ID查询订单详情
     */
    @GetMapping("/select_by_id")
    public Result<OrderDetail> selectById(@RequestParam Long id) {
        OrderDetail orderDetail = orderDetailService.getById(id);
        return Result.success(orderDetail);
    }

    /**
     * 根据订单ID查询订单详情列表
     */
    @GetMapping("/get_by_order_id")
    public Result<List<OrderDetail>> getByOrderId(@RequestParam Long orderId) {
        List<OrderDetail> list = orderDetailService.getByOrderId(orderId);
        return Result.success(list);
    }
}

