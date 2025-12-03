package com.ticketsystem.dto;

import lombok.Data;

/**
 * 改签订单DTO
 */
@Data
public class ChangeOrderDTO {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 新车次ID
     */
    private Long newTrainId;
}

