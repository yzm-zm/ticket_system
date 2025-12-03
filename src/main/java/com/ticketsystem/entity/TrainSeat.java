package com.ticketsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车次座位实体类
 */
@Data
@TableName("train_seat")
public class TrainSeat {
    /**
     * 车次座位ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车次ID
     */
    private Long trainId;

    /**
     * 座位类型ID
     */
    private Long seatTypeId;

    /**
     * 总座位数
     */
    private Integer totalSeats;

    /**
     * 可用座位数
     */
    private Integer availableSeats;

    /**
     * 票价
     */
    private BigDecimal price;

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

