package com.ticketsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 座位类型实体类
 */
@Data
@TableName("seat_type")
public class SeatType {
    /**
     * 座位类型ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 座位类型名称（商务座、一等座、二等座等）
     */
    private String typeName;

    /**
     * 座位类型代码
     */
    private String typeCode;

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

