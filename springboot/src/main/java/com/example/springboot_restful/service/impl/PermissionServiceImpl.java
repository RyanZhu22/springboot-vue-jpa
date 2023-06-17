package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.common.error.ResultEnum;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.exception.BusinessException;
import com.example.springboot_restful.repository.PermissionRepository;
import com.example.springboot_restful.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Permission permission) {
        // check if the permission existed
        this.findById(permission.getId());
        return permissionRepository.save(permission);
    }

    @Override
    public void deleteById(Integer id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Permission findById(Integer id) {
        Optional<Permission> optPermission = permissionRepository.findById(id);
        if (optPermission.isEmpty()) {
            throw new BusinessException(ResultEnum.NOT_EXIST);
        }
        return optPermission.get();
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Long count() {
        return permissionRepository.count();
    }


    @Override
    public List<Permission> tree() {
        List<Permission> all = permissionRepository.findAllByDeletedIsFalse();
        return childrenTree(null, all);
    }

    @Override
    public List<Permission> childrenTree(Integer pid, List<Permission> allData) {
        List<Permission> list = new ArrayList<>();
        for (Permission permission : allData) {
            if (Objects.equals(permission.getPid(), pid)) { // null, 一级
                list.add(permission);
                List<Permission> childrenTree = childrenTree(permission.getId(), allData); // 递归 摘取二级节点\三级
                permission.setChildren(childrenTree);
            }
        }
        return list;
    }

    @Override
    public boolean updateHide(Integer id, boolean hide) {
        Permission dbPermission = this.findById(id);
        if (dbPermission == null) {
            return false;
        }
        dbPermission.setHide(hide);
        this.save(dbPermission);
        return true;
    }
}
