package com.example.springboot_restful.service;


import com.example.springboot_restful.controller.dto.LoginDTO;
import com.example.springboot_restful.controller.dto.UserDTO;
import com.example.springboot_restful.controller.dto.UserRequest;
import com.example.springboot_restful.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findByUsername(String username);

    User findByUserId(Integer id);

    User registerByUsername(String username);

    // 真删除
    int deleteByIdT(Integer id);

    // 假删除
    int deleteByIdF(Integer id);

    int update(User user);

    List<User> selectPage(Integer pageNum, Integer pageSize, String username, String email, String address, Integer deleted);

    // 查询总条数
    Integer selectTotal(String username, String email, String address, Integer deleted);

    // 插入（批量）
    int saveBatch(List<User> list);

    LoginDTO login(UserRequest user);

    void register(UserRequest user);

    User saveUser(User user);
}
