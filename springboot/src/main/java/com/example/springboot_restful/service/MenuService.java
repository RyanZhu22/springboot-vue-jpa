package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    int saveOrUpdate(Menu menu);

    int removeById(Integer id);

    Menu getById(Integer id);

    int totalCount();

    Map<String, Object> findPage(Integer pageNum, Integer pageSize);

    List<Menu> findAll();
}
