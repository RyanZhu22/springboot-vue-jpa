package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Menu;
import com.example.springboot_restful.mapper.MenuMapper;
import com.example.springboot_restful.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public int saveOrUpdate(Menu menu) {
        return menuMapper.saveOrUpdate(menu);
    }

    @Override
    public int removeById(Integer id) {
        return menuMapper.removeById(id);
    }

    @Override
    public Menu getById(Integer id) {
        return menuMapper.getById(id);
    }

    @Override
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize) {
        int total = this.totalCount();
        pageNum = (pageNum - 1) * pageSize;
        List<Menu> menuList = menuMapper.findPage(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("data", menuList);
        return res;
    }

    @Override
    public int totalCount() {
        return menuMapper.totalCount();
    }

    @Override
    public List<Menu> findAll() {
        // 查询所有数据
        List<Menu> list = menuMapper.findAll();
        // 找出pid为null的一级菜单
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu menu : parentNode) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNode;
    }
}
