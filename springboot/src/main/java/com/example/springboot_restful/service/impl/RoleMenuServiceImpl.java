package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.RoleMenu;
import com.example.springboot_restful.mapper.RoleMenuMapper;
import com.example.springboot_restful.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public int deleteByRoleId(Integer roleId) {
        return roleMenuMapper.deleteByRoleId(roleId);
    }

    @Override
    public int insert(RoleMenu roleMenu) {
        return roleMenuMapper.insert(roleMenu);
    }

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        // 先删除当前角色id所有绑定关系
        this.deleteByRoleId(roleId);

        // 再把前端传输的菜单id数组绑定在当前角色id
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            this.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> selectByRoleId(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return this.selectByRoleId(roleId);
    }
}
