package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.entity.Station;
import com.ticketsystem.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车站控制器
 */
@RestController
@RequestMapping("/api/station")
public class StationController {

    @Autowired
    private StationService stationService;

    /**
     * 分页查询车站
     */
    @PostMapping("/query_page")
    public Result<PageResult<Station>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) Station station) {
        Page<Station> page = stationService.queryPage(current, size, station);
        PageResult<Station> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除车站
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        stationService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存车站（新增或更新）
     */
    @PostMapping("/save")
    public Result<Station> save(@RequestBody Station station) {
        stationService.saveOrUpdate(station);
        return Result.success(station);
    }

    /**
     * 根据ID查询车站
     */
    @GetMapping("/select_by_id")
    public Result<Station> selectById(@RequestParam Long id) {
        Station station = stationService.getById(id);
        return Result.success(station);
    }

    /**
     * 查询所有启用的车站
     */
    @GetMapping("/list_all")
    public Result<List<Station>> listAll() {
        List<Station> list = stationService.lambdaQuery().eq(Station::getStatus, 1).list();
        return Result.success(list);
    }
}

