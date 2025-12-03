package com.ticketsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticketsystem.entity.User;
import com.ticketsystem.mapper.UserMapper;
import com.ticketsystem.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Page<User> queryPage(Long current, Long size, User user) {
        Page<User> page = new Page<>(current, size);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (user != null) {
            if (StringUtils.hasText(user.getUsername())) {
                wrapper.like(User::getUsername, user.getUsername());
            }
            if (StringUtils.hasText(user.getRealName())) {
                wrapper.like(User::getRealName, user.getRealName());
            }
            if (user.getStatus() != null) {
                wrapper.eq(User::getStatus, user.getStatus());
            }
        }
        wrapper.orderByDesc(User::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public User login(String username, String password) {
        User user = getByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}

