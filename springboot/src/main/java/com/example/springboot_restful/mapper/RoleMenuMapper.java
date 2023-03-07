package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMenuMapper {

    int deleteByRoleId(Integer roleId);

    int insert(RoleMenu roleMenu);

    List<Integer> selectByRoleId(Integer roleId);
}
