package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.RoleMenu;

import java.util.List;

public interface RoleMenuService {

    int deleteByRoleId(Integer roleId);

    int insert(RoleMenu roleMenu);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> selectByRoleId(Integer roleId);

    List<Integer> getRoleMenu(Integer roleId);
}
