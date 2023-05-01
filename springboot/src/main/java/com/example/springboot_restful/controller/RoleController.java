package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping
    public ResultBody findAll() {
        List<Role> roleList = roleService.findAll();
        return ResultBody.success(roleList);
    }

    @PostMapping("/save")
    public ResultBody save(@RequestBody Role role) {
        roleService.save(role);
        return ResultBody.success();
    }

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Role role) {
        // save or update role
        roleService.save(role);
        // save the relationship between role and permission
        roleService.savePermissions(role.getId(), role.getPermissionIds());
        return ResultBody.success();
    }

    @PutMapping("/{id}")
    public ResultBody updateDeletedById(@PathVariable Integer id) {
        roleService.updateDeletedById(id);
        return ResultBody.success();
    }

    @DeleteMapping("/{id}")
    public ResultBody deleteById(@PathVariable Integer id) {
        roleService.deleteById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable Integer id) {
        return ResultBody.success(roleService.findById(id).get());
    }

    @GetMapping("/page")
    public ResultBody findPage(@RequestParam(required = false) String name,
                               @RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        Map<String, Object> res = roleService.findPage(name, pageNum - 1, pageSize);
        return ResultBody.success(res);
    }

    @PostMapping("/rolePermission/{roleId}")
    public ResultBody rolePermission(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        rolePermissionService.setRolePermission(roleId, menuIds);
        return ResultBody.success();
    }

    @GetMapping("/rolePermission/{roleId}")
    public ResultBody getRolePermission(@PathVariable Integer roleId) {
        return ResultBody.success(rolePermissionService.findRolePermission(roleId));
    }
}
