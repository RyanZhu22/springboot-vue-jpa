package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.mapper.PermissionMapper;
import com.example.springboot_restful.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public int saveOrUpdate(Permission Permission) {
        return permissionMapper.saveOrUpdate(Permission);
    }

    @Override
    public int removeById(Integer id) {
        return permissionMapper.removeById(id);
    }

    @Override
    public Permission getById(Integer id) {
        return permissionMapper.getById(id);
    }

    @Override
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize) {
        int total = this.totalCount();
        pageNum = (pageNum - 1) * pageSize;
        List<Permission> permissionList = permissionMapper.findPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("data", permissionList);
        return res;
    }

    @Override
    public List<Permission> findAll() {
        return permissionMapper.findAll();
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
                permission.setChildren(childrenTree);
            }
        }
        return list;
    }

    @Override
    public int updateHide(Permission permission) {
        return permissionMapper.updateHide(permission);
    }


    @Override
    public int totalCount() {
        return permissionMapper.totalCount();
    }

}
