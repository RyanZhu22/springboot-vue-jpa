package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.mapper.RoleMapper;
import com.example.springboot_restful.service.RoleMenuService;
import com.example.springboot_restful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public int saveOrUpdate(Role role) {
        return roleMapper.saveOrUpdate(role);
    }

    @Override
    public int removeById(Integer id) {
        return roleMapper.removeById(id);
    }

    @Override
    public Role getById(Integer id) {
        return roleMapper.getById(id);
    }

    @Override
    public Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize) {
        int total = this.totalCount();
        pageNum = (pageNum - 1) * pageSize;
        List<Role> roleList = roleMapper.findPage(name, pageNum, pageSize);;
        // map格式放回数据
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("data", roleList);
        return res;
    }

    @Override
    public int totalCount() {
        return roleMapper.totalCount();
    }
}
