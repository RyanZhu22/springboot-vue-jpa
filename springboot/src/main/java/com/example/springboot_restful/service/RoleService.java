package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Role;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoleService {

    void save(Role role);

    void deleteById(Integer id);

    void updateDeletedById(Integer id);

    Optional<Role> findById(Integer id);

    Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize);

    long count();

    List<Role> findAll();

    Role findByFlag(String flag);

    void savePermissions(Integer roleId, List<Integer> permissionIds);
}
