package com.ticketsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单详情实体类
 */
@Data
@TableName("order_detail")
public class OrderDetail {
    /**
     * 订单详情ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 乘车人姓名
     */
    private String passengerName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 座位类型ID
     */
    private Long seatTypeId;

    /**
     * 座位号
     */
    private String seatNo;

    /**
     * 票价
     */
    private BigDecimal price;

    /**
     * 状态：1-正常，0-已退票，2-已改签
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

