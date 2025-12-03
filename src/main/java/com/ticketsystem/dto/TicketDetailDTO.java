package com.ticketsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 电子票详情DTO
 */
@Data
public class TicketDetailDTO {
    /**
     * 乘车人姓名
     */
    private String passengerName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 座位类型名称
     */
    private String seatTypeName;

    /**
     * 座位号
     */
    private String seatNo;

    /**
     * 票价
     */
    private BigDecimal price;
}

