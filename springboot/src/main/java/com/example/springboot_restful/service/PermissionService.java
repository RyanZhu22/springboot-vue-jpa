package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    Permission save(Permission permission);

    Permission update(Permission permission);

    void deleteById(Integer id);

    Permission findById(Integer id);

    List<Permission> findAll();

    Long count();

    List<Permission> tree();

    List<Permission> childrenTree(Integer pid, List<Permission> allData);

    boolean updateHide(Integer id, boolean hide);
}
