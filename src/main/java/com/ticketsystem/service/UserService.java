package com.ticketsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticketsystem.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    /**
     * 分页查询用户
     */
    Page<User> queryPage(Long current, Long size, User user);

    /**
     * 根据用户名查询用户
     */
    User getByUsername(String username);

    /**
     * 用户登录
     */
    User login(String username, String password);
}

