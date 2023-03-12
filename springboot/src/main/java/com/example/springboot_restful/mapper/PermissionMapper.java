package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface PermissionMapper {

    int saveOrUpdate(Permission permission);

    int removeById(Integer id);

    Permission getById(Integer id);

    int totalCount();

    List<Permission> findPage(Integer pageNum, Integer pageSize);

    List<Permission> findAll();

    int updateHide(Permission permission);

}
