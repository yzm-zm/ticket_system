package com.ticketsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车站实体类
 */
@Data
@TableName("station")
public class Station {
    /**
     * 车站ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 车站名称
     */
    private String stationName;

    /**
     * 车站代码
     */
    private String stationCode;

    /**
     * 所属城市
     */
    private String city;

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

