package com.example.springboot_restful.service;


import com.example.springboot_restful.dto.LoginResponse;
import com.example.springboot_restful.dto.UserRequest;
import com.example.springboot_restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Long count();

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Integer id);

    void deleteById(Integer id);

    void updateDeleted(Integer id);

    User update(User user);

    User partialUpdate(User user);

    void updateAvatar(User user);

    Page<User> findAll(Pageable pageable);

    List<User> findByConditions(String username, String email, String address);

    Page<User> findByConditionsWithPagination(Pageable pageable, String username, String email, String address);

    // 条件查询总条数
    Long selectTotal(String username, String email, String address, Integer deleted);

    // 插入（批量）
    List<User> saveBatch(List<User> list);

    LoginResponse getUserPermissionsAndAuths(User user);

    void logout(String uid);

    String passwordReset(UserRequest user);

    void passwordChange(UserRequest user);

    User save(User user);

    User saveUser(User user);

    void validateEmail(String emailKey, String emailCode);
}
