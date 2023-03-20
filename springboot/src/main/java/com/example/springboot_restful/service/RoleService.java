package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

public interface RoleService {

    int saveOrUpdate(Role role);

    int removeById(Integer id);

    Role getById(Integer id);

    Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize);

    int totalCount();

    List<Role> findAll();

    void savePermissions(Integer roleId, List<Integer> permissionIds) throws ServerException;

    Role getByFlag(String flag);
}
