package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.service.RoleMenuService;
import com.example.springboot_restful.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Role role) {
        try {
            roleService.saveOrUpdate(role);
            return ResultBody.success();
        } catch (Exception e) {
            if (e instanceof SQLException) {
                return ResultBody.error("500", "重复添加错误");
            }
            return ResultBody.error("500", "添加错误");
        }
    }

    @PostMapping("/{id}")
    public ResultBody removeById(@PathVariable Integer id) {
        roleService.removeById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable Integer id) {
        Role role = roleService.getById(id);
        return ResultBody.success(role);
    }

    @GetMapping("/page")
    public ResultBody findPage(@RequestParam(required = false) String name,
                               @RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        Map<String, Object> res = roleService.findPage(name, pageNum, pageSize);
        return ResultBody.success(res);
    }

    @PostMapping("/roleMenu/{roleId}")
    public ResultBody roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleMenuService.setRoleMenu(roleId, menuIds);
        return ResultBody.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public ResultBody getRoleMenu(@PathVariable Integer roleId) {
        return ResultBody.success(roleMenuService.getRoleMenu(roleId));
    }
}
