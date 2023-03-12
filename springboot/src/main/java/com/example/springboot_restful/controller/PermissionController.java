package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.service.DictService;
import com.example.springboot_restful.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping
    public ResultBody updateHide(@RequestBody Permission permission) {
        permissionService.updateHide(permission);
        return ResultBody.success();
    }

    @PostMapping
    public ResultBody saveOrUpdate(@RequestBody Permission Permission) {
        permissionService.saveOrUpdate(Permission);
        return ResultBody.success();
    }

    @PostMapping("/{id}")
    public ResultBody removeById(@PathVariable Integer id) {
        permissionService.removeById(id);
        return ResultBody.success();
    }

    @GetMapping("/{id}")
    public ResultBody findOne(@PathVariable Integer id) {
        Permission Permission = permissionService.getById(id);
        return ResultBody.success(Permission);
    }

    @GetMapping("/icons")
    public ResultBody getIcons() {
        return ResultBody.success(dictService.findIcon());
    }

}
