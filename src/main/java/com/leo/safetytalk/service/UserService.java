package com.leo.safetytalk.service;

import com.leo.safetytalk.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author leo
 * @since 2024-01-22
 */
public interface UserService extends IService<User> {
    boolean isUserExistByUsername(String username);
    User loadUserByUsername(String username);
}
