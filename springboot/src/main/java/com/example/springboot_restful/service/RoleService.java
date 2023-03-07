package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    int saveOrUpdate(Role role);

    int removeById(Integer id);

    Role getById(Integer id);

    Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize);

    int totalCount();
}
