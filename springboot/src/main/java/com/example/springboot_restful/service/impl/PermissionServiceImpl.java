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
    public List<Permission> findAll(String name) {
        return permissionMapper.findAll(name);
    }

    @Override
    public List<Permission> tree(String name) {
        List<Permission> allData = this.findAll(name);
        List<Permission> parentList = allData.stream().filter(p -> p.getPid() == null).collect(Collectors.toList());
        for (Permission permission : parentList) {
            List<Permission> level2List = allData.stream().filter(p -> Objects.equals(p.getPid(), permission.getId())).collect(Collectors.toList());
            permission.setChildren(level2List);
            for (Permission permission1 : level2List) {
                List<Permission> level3List = allData.stream().filter(p -> Objects.equals(p.getPid(), permission1.getId())).collect(Collectors.toList());
                permission1.setChildren(level3List);
            }
        }
        return parentList;
    }

    @Override
    public List<Permission> childrenTree(Integer pid, List<Permission> allData) {
        List<Permission> list = new ArrayList<>();
        if (pid == null) {
            return allData.stream().filter(p -> p.getPid() == null).collect(Collectors.toList());
        }
        return allData.stream().filter(p -> Objects.equals(p.getPid(), pid)).collect(Collectors.toList());
    }


    @Override
    public int totalCount() {
        return permissionMapper.totalCount();
    }

}
