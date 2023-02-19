package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    int saveOrUpdate(Menu menu);

    int removeById(Integer id);

    Menu getById(Integer id);

    int totalCount();

    List<Menu> findPage(Integer pageNum, Integer pageSize);

    List<Menu> findAll();
}
