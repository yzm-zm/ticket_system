package com.ticketsystem.dto;

import lombok.Data;

/**
 * 订单详情DTO
 */
@Data
public class OrderDetailDTO {
    /**
     * 座位类型ID
     */
    private Long seatTypeId;

    /**
     * 乘车人姓名
     */
    private String passengerName;

    /**
     * 身份证号
     */
    private String idCard;
}

