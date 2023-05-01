package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.RolePermission;
import com.example.springboot_restful.repository.RolePermissionRepository;
import com.example.springboot_restful.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public void save(RolePermission rolePermission) {
        rolePermissionRepository.save(rolePermission);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        rolePermissionRepository.deleteByRoleId(roleId);
    }


    @Transactional
    @Override
    public List<RolePermission> setRolePermission(Integer roleId, List<Integer> permissionIds) {
        // 先删除当前角色id所有绑定关系
        rolePermissionRepository.deleteByRoleId(roleId);

        // 再把前端传输的菜单id数组绑定在当前角色id
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Integer permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermissionRepository.save(rolePermission));
        }

        return rolePermissions;
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer roleId) {
        return rolePermissionRepository.findPermissionIdsByRoleId(roleId);
    }

    @Override
    public List<RolePermission> findRolePermission(Integer permissionId) {
        return rolePermissionRepository.findByPermissionId(permissionId);
    }

    @Override
    public List<RolePermission> findAll() {
        return rolePermissionRepository.findAll();
    }

}
