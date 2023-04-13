package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    @Query("SELECT rp.permissionId FROM RolePermission rp WHERE rp.roleId = ?1")
    List<Integer> findPermissionIdsByRoleId(Integer roleId);

    void deleteByRoleId(Integer roleId);

    List<RolePermission> findByPermissionId(Integer permissionId);
}
