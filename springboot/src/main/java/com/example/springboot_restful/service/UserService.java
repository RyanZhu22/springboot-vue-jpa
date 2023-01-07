package com.example.springboot_restful.service;

import com.example.springboot_restful.common.JsonResult;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public JsonResult<User> save(User user) {
        // 如果返回false
        if (userMapper.findByUsername(user.getUsername()) == null) {
            if (user.getId() == null) {
                userMapper.insert(user);
            } else {
                userMapper.update(user);
            }
            return new JsonResult<>(user);
        }
        return new JsonResult<>(false);
    }

    // 批量删除
    public int saveBatch(List<User> list) {
        try {
            return userMapper.saveBatch(list);
        } catch (Exception e) {
            return -1;
        }
    }

}