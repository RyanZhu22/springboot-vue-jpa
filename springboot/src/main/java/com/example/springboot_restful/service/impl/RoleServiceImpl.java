package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.RolePermission;
import com.example.springboot_restful.exception.ServiceException;
import com.example.springboot_restful.repository.RoleRepository;
import com.example.springboot_restful.service.RolePermissionService;
import com.example.springboot_restful.service.RoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionService rolePermissionService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void updateDeletedById(Integer id) {
        roleRepository.updateDeletedById(id);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        Optional<Role> opt = roleRepository.findById(id);
        if (opt.isEmpty()) {
            throw new ServiceException("The role is not found");
        }
        return opt;
    }

    @Override
    public List<Role> findAll() {
        List<Role> roleList = roleRepository.findAll();
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
        return roleList;
    }

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public Map<String, Object> findPage(String name, Integer pageNum, Integer pageSize) {
        Long total = roleRepository.count();
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
    public void savePermissions(Integer roleId, List<Integer> permissionIds) {
        // check if the data is empty
        if (permissionIds == null || roleId == null) {
            throw new RuntimeException("数据不能为空");
        }

        // delete all relationship between role and permission according to roleId
        rolePermissionService.deleteByRoleId(roleId);

        // Ensure that the delete operation is completed before proceeding to the next step
        entityManager.flush();

        // insert relationship data from front-end
        permissionIds.forEach(v -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(v);
            rolePermissionService.save(rolePermission);
        });
    }
}
