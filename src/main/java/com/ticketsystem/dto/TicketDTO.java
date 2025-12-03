package com.ticketsystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 电子票DTO
 */
@Data
public class TicketDTO {
    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 车次号
     */
    private String trainNo;

    /**
     * 出发站
     */
    private String departureStationName;

    /**
     * 到达站
     */
    private String arrivalStationName;

    /**
     * 出发时间
     */
    private LocalDateTime departureTime;

    /**
     * 到达时间
     */
    private LocalDateTime arrivalTime;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 乘车人信息列表
     */
    private List<TicketDetailDTO> passengers;
}

