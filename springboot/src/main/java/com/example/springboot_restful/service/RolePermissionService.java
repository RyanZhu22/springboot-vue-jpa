package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.RolePermission;

import java.util.List;

public interface RolePermissionService {
    RolePermission save(RolePermission rolePermission);

    void deleteByRoleId(Integer roleId);

    List<RolePermission> setRoleMenu(Integer roleId, List<Integer> permissionIds);

    List<Integer> findPermissionIdsByRoleId(Integer roleId);

    List<RolePermission> findRoleMenu(Integer permissionId);

    List<RolePermission> findAll();
}
