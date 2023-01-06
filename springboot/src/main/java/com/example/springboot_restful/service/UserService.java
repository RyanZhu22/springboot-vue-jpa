package com.example.springboot_restful.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.*;
import com.example.springboot_restful.controller.dto.UserDTO;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public int save(User user) {
        if (user.getId() == null) {
            return userMapper.insert(user);
        } else {
            return userMapper.update(user);
        }
    }

    // 批量删除
    public int saveBatch(List<User> list) {
        try {
            return userMapper.saveBatch(list);
        } catch (Exception e) {
            return -1;
        }
    }

    public boolean login(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return false;
    }
}
