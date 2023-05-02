package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.service.DictService;
import com.example.springboot_restful.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private DictService dictService;

    @GetMapping
    public ResultBody findAll() {
        List<Permission> permissionList = permissionService.findAll();
        return ResultBody.success(permissionList);
    }

    @GetMapping("/tree")
    public ResultBody tree() {
        List<Permission> permissionList = permissionService.tree();
        return ResultBody.success(permissionList);
    }

    @PostMapping("/hide")
    public ResultBody updateHide(@RequestBody Permission permission) {
        permissionService.updateHide(permission.getId(), permission.getHide());
        return ResultBody.success();
    }

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Permission Permission) {
        permissionService.save(Permission);
        return ResultBody.success();
    }

    @PostMapping("/{id}")
    public ResultBody removeById(@PathVariable Integer id) {
        permissionService.deleteById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findById(@PathVariable Integer id) {
        Permission permission = permissionService.findById(id);
        return ResultBody.success(permission);
    }

    @GetMapping("/icons")
    public ResultBody getIcons() {
        return ResultBody.success(dictService.findIcon());
    }

}
