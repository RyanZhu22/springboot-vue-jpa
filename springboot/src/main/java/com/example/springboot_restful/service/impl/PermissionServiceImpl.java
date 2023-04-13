package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.repository.PermissionRepository;
import com.example.springboot_restful.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public void deleteById(Integer id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Permission findById(Integer id) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        return optionalPermission.orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
    }

    @Override
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize) {
        Long total = this.count();
        Page<Permission> page = permissionRepository.findAll(PageRequest.of(pageNum, pageSize));
        List<Permission> permissionList = page.getContent();
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("data", permissionList);
        return res;
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
    public Permission updateHide(Integer id, Boolean hide) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (optionalPermission.isPresent()) {
            Permission permission = optionalPermission.get();
            permission.setHide(hide);
            return permissionRepository.save(permission);
        } else {
            throw new RuntimeException("Permission not found with id: " + id);
        }
    }

    @Override
    public List<Permission> tree() {
        List<Permission> allData = this.findAll();
        return childrenTree(null, allData);
    }

    @Override
    public List<Permission> childrenTree(Integer pid, List<Permission> allData) {
        List<Permission> list = new ArrayList<>();
        for (Permission permission : allData) {
            if (Objects.equals(permission.getPid(), pid)) { // null, 一级
                list.add(permission);
                List<Permission> childrenTree = childrenTree(permission.getId(), allData); // 递归 摘取二级节点\三级
            }
        }
        return list;
    }
}
