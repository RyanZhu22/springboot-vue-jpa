package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<RolePermission> rolePermissionList = rolePermissionService.findAll();
        List<RolePermission> filteredRolePermission = new ArrayList<>();
        List<Integer> permissionIds = new ArrayList<>();
        // for each role list
        roleList.forEach(v -> {
            for (RolePermission rolePermission : rolePermissionList) {
                if (rolePermission.getRoleId().equals(v.getId())) {
                    filteredRolePermission.add(rolePermission);
                }
            }
            System.out.println(filteredRolePermission);
            for (RolePermission rolePermission : filteredRolePermission) {
                permissionIds.add(rolePermission.getPermissionId());
            }
            System.out.println(filteredRolePermission);
            System.out.println(permissionIds);
            v.setPermissionIds(permissionIds);
        });
        return ResultBody.success(roleList);
    }

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Role role) throws ServerException {
        roleService.save(role);
        // save the relationship between role and permission
        roleService.savePermissions(role.getId(), role.getPermissionIds());
        return ResultBody.success();
    }

    @PostMapping("/{id}")
    public ResultBody removeById(@PathVariable Integer id) {
        roleService.deleteById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable Integer id) {
        Role role = roleService.findById(id);
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
        rolePermissionService.setRoleMenu(roleId, menuIds);
        return ResultBody.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public ResultBody getRoleMenu(@PathVariable Integer roleId) {
        return ResultBody.success(rolePermissionService.findRoleMenu(roleId));
    }
}
