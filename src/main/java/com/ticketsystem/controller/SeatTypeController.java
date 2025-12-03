package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.entity.SeatType;
import com.ticketsystem.service.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 座位类型控制器
 */
@RestController
@RequestMapping("/api/seat_type")
public class SeatTypeController {

    @Autowired
    private SeatTypeService seatTypeService;

    /**
     * 分页查询座位类型
     */
    @PostMapping("/query_page")
    public Result<PageResult<SeatType>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) SeatType seatType) {
        Page<SeatType> page = seatTypeService.queryPage(current, size, seatType);
        PageResult<SeatType> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除座位类型
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        seatTypeService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存座位类型（新增或更新）
     */
    @PostMapping("/save")
    public Result<SeatType> save(@RequestBody SeatType seatType) {
        seatTypeService.saveOrUpdate(seatType);
        return Result.success(seatType);
    }

    /**
     * 根据ID查询座位类型
     */
    @GetMapping("/select_by_id")
    public Result<SeatType> selectById(@RequestParam Long id) {
        SeatType seatType = seatTypeService.getById(id);
        return Result.success(seatType);
    }

    /**
     * 查询所有启用的座位类型
     */
    @GetMapping("/list_all")
    public Result<List<SeatType>> listAll() {
        List<SeatType> list = seatTypeService.lambdaQuery().eq(SeatType::getStatus, 1).list();
        return Result.success(list);
    }
}

