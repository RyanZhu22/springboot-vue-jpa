package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    int saveOrUpdate(Permission permission);

    int removeById(Integer id);

    Permission getById(Integer id);

    int totalCount();

    Map<String, Object> findPage(Integer pageNum, Integer pageSize);

    List<Permission> findAll(String name);

    List<Permission> tree(String name);

    List<Permission> childrenTree(Integer pid, List<Permission> allData);
}
