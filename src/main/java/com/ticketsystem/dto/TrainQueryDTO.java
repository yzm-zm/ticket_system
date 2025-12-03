package com.ticketsystem.dto;

import lombok.Data;

/**
 * 车次查询DTO
 */
@Data
public class TrainQueryDTO {
    /**
     * 出发站ID
     */
    private Long departureStationId;

    /**
     * 到达站ID
     */
    private Long arrivalStationId;

    /**
     * 出发日期（格式：yyyy-MM-dd）
     */
    private String departureDate;
}

