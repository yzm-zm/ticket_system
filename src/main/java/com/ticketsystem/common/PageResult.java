package com.ticketsystem.common;

import lombok.Data;

import java.util.List;

/**
 * 分页查询结果类
 */
@Data
public class PageResult<T> {
    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页数量
     */
    private Long size;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> records;

    public PageResult() {
    }

    public PageResult(Long current, Long size, Long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
    }
}

