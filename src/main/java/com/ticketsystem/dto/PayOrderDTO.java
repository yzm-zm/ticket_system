package com.ticketsystem.dto;

import lombok.Data;

/**
 * 支付订单DTO
 */
@Data
public class PayOrderDTO {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 支付方式
     */
    private String payType;
}

