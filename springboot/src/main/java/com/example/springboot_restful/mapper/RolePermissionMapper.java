package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper {

    int deleteByRoleId(Integer roleId);

    int insert(RolePermission rolePermission);

    List<Integer> selectByRoleId(Integer roleId);

    List<RolePermission> findAll();
}
