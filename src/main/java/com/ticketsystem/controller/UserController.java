package com.ticketsystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticketsystem.common.PageResult;
import com.ticketsystem.common.Result;
import com.ticketsystem.dto.LoginDTO;
import com.ticketsystem.entity.User;
import com.ticketsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户
     */
    @PostMapping("/query_page")
    public Result<PageResult<User>> queryPage(@RequestParam Long current, @RequestParam Long size, @RequestBody(required = false) User user) {
        Page<User> page = userService.queryPage(current, size, user);
        PageResult<User> pageResult = new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除用户
     */
    @PostMapping("/delete_by_id")
    public Result<Void> deleteById(@RequestParam Long id) {
        userService.removeById(id);
        return Result.success(null);
    }

    /**
     * 保存用户（新增或更新）
     */
    @PostMapping("/save")
    public Result<User> save(@RequestBody User user) {
        if (user.getId() == null) {
            // 新增时检查用户名是否已存在
            User existUser = userService.getByUsername(user.getUsername());
            if (existUser != null) {
                return Result.error("用户名已存在");
            }
        }
        userService.saveOrUpdate(user);
        return Result.success(user);
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/select_by_id")
    public Result<User> selectById(@RequestParam Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }
        return Result.success(user);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        User existUser = userService.getByUsername(user.getUsername());
        if (existUser != null) {
            return Result.error("用户名已存在");
        }
        userService.save(user);
        return Result.success(user);
    }
}

