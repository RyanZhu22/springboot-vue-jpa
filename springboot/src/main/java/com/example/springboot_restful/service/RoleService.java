package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;

import java.rmi.ServerException;
import java.util.List;
import java.util.Map;

public interface RoleService {

    Role save(Role role);

    void deleteById(Integer id);

    Role findById(Integer id);

    Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize);

    long count();

    List<Role> findAll();

    Role findByFlag(String flag);

    void savePermissions(Integer roleId, List<Integer> permissionIds) throws ServerException;
}
