package com.ticketsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车次实体类
 */
@Data
@TableName("train")
public class Train {
    /**
     * 车次ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车次号
     */
    private String trainNo;

    /**
     * 车次名称
     */
    private String trainName;

    /**
     * 出发站ID
     */
    private Long departureStationId;

    /**
     * 到达站ID
     */
    private Long arrivalStationId;

    /**
     * 出发时间
     */
    private LocalDateTime departureTime;

    /**
     * 到达时间
     */
    private LocalDateTime arrivalTime;

    /**
     * 运行时长（分钟）
     */
    private Integer duration;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

