package com.ticketsystem.dto;

import lombok.Data;

import java.util.List;

/**
 * 创建订单DTO
 */
@Data
public class CreateOrderDTO {
    /**
     * 车次ID
     */
    private Long trainId;

    /**
     * 乘车人信息列表
     */
    private List<OrderDetailDTO> passengers;
}

