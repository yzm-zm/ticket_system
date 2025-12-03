package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.dto.TrainQueryDTO;
import com.ticketsystem.entity.Train;
import com.ticketsystem.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 车次控制器
 */
@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    /**
     * 分页查询车次
     */
    @PostMapping("/query_page")
    public Result<PageResult<Train>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) Train train) {
        Page<Train> page = trainService.queryPage(current, size, train);
        PageResult<Train> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除车次
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        trainService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存车次（新增或更新）
     */
    @PostMapping("/save")
    public Result<Train> save(@RequestBody Train train) {
        trainService.saveOrUpdate(train);
        return Result.success(train);
    }

    /**
     * 根据ID查询车次
     */
    @GetMapping("/select_by_id")
    public Result<Train> selectById(@RequestParam Long id) {
        Train train = trainService.getById(id);
        return Result.success(train);
    }

    /**
     * 查询车次（包含座位信息）
     */
    @PostMapping("/query_with_seats")
    public Result<PageResult<Train>> queryTrainWithSeats(@RequestParam Long current, @RequestParam Long size, @RequestBody TrainQueryDTO queryDTO) {
        Page<Train> page = trainService.queryTrainWithSeats(
                current, size,
                queryDTO.getDepartureStationId(),
                queryDTO.getArrivalStationId(),
                queryDTO.getDepartureDate()
        );
        PageResult<Train> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }
}

