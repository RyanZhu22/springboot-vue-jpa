package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.RolePermission;

import java.util.List;

public interface RolePermissionService {

    int deleteByRoleId(Integer roleId);

    int insert(RolePermission rolePermission);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> selectByRoleId(Integer roleId);

    List<Integer> getRoleMenu(Integer roleId);

    List<RolePermission> findAll();
}
