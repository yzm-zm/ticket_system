package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.entity.TrainSeat;
import com.ticketsystem.service.TrainSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车次座位控制器
 */
@RestController
@RequestMapping("/api/train_seat")
public class TrainSeatController {

    @Autowired
    private TrainSeatService trainSeatService;

    /**
     * 分页查询车次座位
     */
    @PostMapping("/query_page")
    public Result<PageResult<TrainSeat>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) TrainSeat trainSeat) {
        Page<TrainSeat> page = trainSeatService.queryPage(current, size, trainSeat);
        PageResult<TrainSeat> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除车次座位
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        trainSeatService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存车次座位（新增或更新）
     */
    @PostMapping("/save")
    public Result<TrainSeat> save(@RequestBody TrainSeat trainSeat) {
        trainSeatService.saveOrUpdate(trainSeat);
        return Result.success(trainSeat);
    }

    /**
     * 根据ID查询车次座位
     */
    @GetMapping("/select_by_id")
    public Result<TrainSeat> selectById(@RequestParam Long id) {
        TrainSeat trainSeat = trainSeatService.getById(id);
        return Result.success(trainSeat);
    }

    /**
     * 根据车次ID查询座位信息
     */
    @GetMapping("/get_by_train_id")
    public Result<List<TrainSeat>> getByTrainId(@RequestParam Long trainId) {
        List<TrainSeat> list = trainSeatService.getByTrainId(trainId);
        return Result.success(list);
    }
}

