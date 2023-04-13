package com.example.springboot_restful.service;


import com.example.springboot_restful.dto.LoginResponse;
import com.example.springboot_restful.dto.RegisterRequest;
import com.example.springboot_restful.dto.UserRequest;
import com.example.springboot_restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    Optional<User> findById(Integer id);

    void deleteById(Integer id);

    void updateDeleted(Integer id);

    User update(User user);

    Page<User> selectPage(String username, String email, String address, Integer deleted, Pageable pageable);

    // 条件查询总条数
    Long selectTotal(String username, String email, String address, Integer deleted);

    // 插入（批量）
    List<User> saveBatch(List<User> list);

    LoginResponse getUserMenusAndAuths(User user);

    void logout(String uid);

    String passwordReset(UserRequest user);

    void passwordChange(UserRequest user);

    User save(User user);

    User saveUser(User user);

    void validateEmail(String emailKey, String emailCode);
}
