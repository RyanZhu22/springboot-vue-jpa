package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    Permission save(Permission permission);

    void deleteById(Integer id);

    Permission findById(Integer id);

    List<Permission> findAll();

    Long count();

    void updateHide(Integer id, Boolean hide);

    Map<String, Object> findPage(Integer pageNum, Integer pageSize);

    List<Permission> tree();

    List<Permission> childrenTree(Integer pid, List<Permission> allData);

    List<Permission> findAllByConditions();
}
