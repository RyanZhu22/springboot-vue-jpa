package com.example.springboot_restful.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;
import com.example.springboot_restful.repository.RoleRepository;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionService rolePermissionService;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RolePermissionService rolePermissionService) {
        this.roleRepository = roleRepository;
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findById(Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize) {
        Long total = this.count();
        Page<Role> page = roleRepository.findAll(PageRequest.of(pageNum, pageSize));
        List<Role> roleList = page.getContent();

        List<RolePermission> rolePermissionList = rolePermissionService.findAll();
        // for each role list
        roleList.forEach(v -> {
            List<Integer> permissionIds = rolePermissionList.stream().filter(r -> r.getRoleId().equals(v.getId()))
                .map(RolePermission::getPermissionId).collect(Collectors.toList());
            v.setPermissionIds(permissionIds);
        });

        // map格式放回数据
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("data", roleList);
        return res;
    }

    @Override
    public Role findByFlag(String flag) {
        return roleRepository.findByFlag(flag);
    }

    @Transactional
    @Override
    public void savePermissions(Integer roleId, List<Integer> permissionIds) throws ServerException {
        // check if the data is empty
        if (CollUtil.isEmpty(permissionIds) || roleId == null) {
            throw new ServerException("数据不能为空");
        }
        // delete all relationship between role and permission according to roleId
        rolePermissionService.deleteByRoleId(roleId);
        // insert relationship data from front-end
        permissionIds.forEach(v -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(v);
            rolePermissionService.save(rolePermission);
        });
    }
}
