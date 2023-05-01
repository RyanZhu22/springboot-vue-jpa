package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.RolePermission;

import java.util.List;

public interface RolePermissionService {
    void save(RolePermission rolePermission);

    void deleteByRoleId(Integer roleId);

    List<RolePermission> setRolePermission(Integer roleId, List<Integer> permissionIds);

    List<Integer> findPermissionIdsByRoleId(Integer roleId);

    List<RolePermission> findRolePermission(Integer permissionId);

    List<RolePermission> findAll();
}
