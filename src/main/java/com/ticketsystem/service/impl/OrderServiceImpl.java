package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.dto.OrderDetailDTO;
import com.ticketsystem.dto.TicketDTO;
import com.ticketsystem.dto.TicketDetailDTO;
import com.ticketsystem.entity.*;
import com.ticketsystem.mapper.*;
import com.ticketsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private TrainSeatMapper trainSeatMapper;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private SeatTypeMapper seatTypeMapper;

    @Override
    public Page<Order> queryPage(Long current, Long size, Order order) {
        Page<Order> page = new Page<>(current, size);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (order != null) {
            if (order.getUserId() != null) {
                wrapper.eq(Order::getUserId, order.getUserId());
            }
            if (order.getOrderStatus() != null) {
                wrapper.eq(Order::getOrderStatus, order.getOrderStatus());
            }
        }
        wrapper.orderByDesc(Order::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Long userId, Long trainId, List<OrderDetailDTO> passengers) {
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderDetailDTO passenger : passengers) {
            TrainSeat trainSeat = trainSeatMapper.selectById(passenger.getSeatTypeId());
            if (trainSeat == null || trainSeat.getAvailableSeats() <= 0) {
                throw new RuntimeException("座位不足");
            }
            totalAmount = totalAmount.add(trainSeat.getPrice());
        }

        // 创建订单
        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setUserId(userId);
        order.setTrainId(trainId);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(0); // 待支付
        this.save(order);

        // 创建订单详情
        for (OrderDetailDTO passenger : passengers) {
            TrainSeat trainSeat = trainSeatMapper.selectById(passenger.getSeatTypeId());
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            detail.setPassengerName(passenger.getPassengerName());
            detail.setIdCard(passenger.getIdCard());
            detail.setSeatTypeId(passenger.getSeatTypeId());
            detail.setPrice(trainSeat.getPrice());
            detail.setStatus(1); // 正常
            orderDetailMapper.insert(detail);

            // 减少可用座位数
            trainSeat.setAvailableSeats(trainSeat.getAvailableSeats() - 1);
            trainSeatMapper.updateById(trainSeat);
        }

        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(Long orderId, String payType) {
        Order order = this.getById(orderId);
        if (order == null || order.getOrderStatus() != 0) {
            return false;
        }
        order.setOrderStatus(1); // 已支付
        order.setPayType(payType);
        order.setPayTime(LocalDateTime.now());
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean refundOrder(Long orderId) {
        Order order = this.getById(orderId);
        if (order == null || order.getOrderStatus() != 1) {
            return false;
        }

        // 更新订单状态
        order.setOrderStatus(2); // 已退票
        this.updateById(order);

        // 更新订单详情状态并释放座位
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> details = orderDetailMapper.selectList(wrapper);
        for (OrderDetail detail : details) {
            detail.setStatus(0); // 已退票
            orderDetailMapper.updateById(detail);

            // 释放座位
            TrainSeat trainSeat = trainSeatMapper.selectById(detail.getSeatTypeId());
            if (trainSeat != null) {
                trainSeat.setAvailableSeats(trainSeat.getAvailableSeats() + 1);
                trainSeatMapper.updateById(trainSeat);
            }
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeOrder(Long orderId, Long newTrainId) {
        Order order = this.getById(orderId);
        if (order == null || order.getOrderStatus() != 1) {
            return false;
        }

        // 退票原订单
        refundOrder(orderId);

        // 创建新订单（简化处理，实际应该更复杂）
        order.setTrainId(newTrainId);
        order.setOrderStatus(3); // 已改签
        this.updateById(order);

        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> details = orderDetailMapper.selectList(wrapper);
        for (OrderDetail detail : details) {
            detail.setStatus(2); // 已改签
            orderDetailMapper.updateById(detail);
        }

        return true;
    }

    @Override
    public TicketDTO getTicketInfo(Long orderId) {
        Order order = this.getById(orderId);
        if (order == null) {
            return null;
        }

        TicketDTO ticket = new TicketDTO();
        ticket.setOrderNo(order.getOrderNo());
        ticket.setTotalAmount(order.getTotalAmount());
        ticket.setPayType(order.getPayType());
        ticket.setPayTime(order.getPayTime());

        // 获取车次信息
        Train train = trainMapper.selectById(order.getTrainId());
        if (train != null) {
            ticket.setTrainNo(train.getTrainNo());
            ticket.setDepartureTime(train.getDepartureTime());
            ticket.setArrivalTime(train.getArrivalTime());

            // 获取车站信息
            Station departureStation = stationMapper.selectById(train.getDepartureStationId());
            Station arrivalStation = stationMapper.selectById(train.getArrivalStationId());
            if (departureStation != null) {
                ticket.setDepartureStationName(departureStation.getStationName());
            }
            if (arrivalStation != null) {
                ticket.setArrivalStationName(arrivalStation.getStationName());
            }
        }

        // 获取订单详情
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        wrapper.eq(OrderDetail::getStatus, 1); // 只查询正常状态的
        List<OrderDetail> details = orderDetailMapper.selectList(wrapper);

        List<TicketDetailDTO> ticketDetails = new java.util.ArrayList<>();
        for (OrderDetail detail : details) {
            TicketDetailDTO ticketDetail = new TicketDetailDTO();
            ticketDetail.setPassengerName(detail.getPassengerName());
            ticketDetail.setIdCard(detail.getIdCard());
            ticketDetail.setSeatNo(detail.getSeatNo());
            ticketDetail.setPrice(detail.getPrice());

            // 获取座位类型名称
            SeatType seatType = seatTypeMapper.selectById(detail.getSeatTypeId());
            if (seatType != null) {
                ticketDetail.setSeatTypeName(seatType.getTypeName());
            }

            ticketDetails.add(ticketDetail);
        }
        ticket.setPassengers(ticketDetails);

        return ticket;
    }
}

