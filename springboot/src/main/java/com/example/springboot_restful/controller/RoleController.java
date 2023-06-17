package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;
import com.example.springboot_restful.entity.User;
import com.example.springboot_restful.model.page.RolePage;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.*;

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
        // create or update role
        roleService.save(role);
        // create the relationship between role and permission
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
    public ResultBody findPage(RolePage rolePage) {
        PageRequest pageable = PageRequest.of(rolePage.getPage() - 1, rolePage.getSize());
        Page<Role> data = roleService.findByConditionsWithPagination(pageable, rolePage.getName());
        return ResultBody.success(data);
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
