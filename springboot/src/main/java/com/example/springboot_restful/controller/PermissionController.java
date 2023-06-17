package com.example.springboot_restful.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.model.dto.common.HideDto;
import com.example.springboot_restful.model.dto.permission.PermissionCreateDto;
import com.example.springboot_restful.model.dto.permission.PermissionUpdateDto;
import com.example.springboot_restful.service.DictService;
import com.example.springboot_restful.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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

    @PostMapping
    public ResultBody save(@RequestBody PermissionCreateDto permissionCreateDto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionCreateDto, permission);
        permissionService.save(permission);
        return ResultBody.success();
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Integer id, @RequestBody PermissionUpdateDto permissionUpdateDto) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionUpdateDto, permission);
        permission.setId(id);
        permissionService.update(permission);
        return ResultBody.success();
    }

    @PostMapping("/{id}")
    public ResultBody deleteById(@PathVariable Integer id) {
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
        List<Dict> icons = dictService.findIcons();
        System.out.println(icons);
        return ResultBody.success(icons);
    }

    @PostMapping("/hide")
    public ResultBody updateHide(@Valid @RequestBody HideDto hideDto) {
        permissionService.updateHide(hideDto.getId(), hideDto.isHide());
        return ResultBody.success();
    }

}
