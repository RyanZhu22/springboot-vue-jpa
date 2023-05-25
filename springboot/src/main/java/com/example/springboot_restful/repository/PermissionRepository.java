package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Permission p SET p.hide = true WHERE p.id = :id")
    void updateHide(Integer id);

    @Query("SELECT p FROM Permission p WHERE p.deleted = false AND p.hide = false")
    List<Permission> findAllByConditions();

    List<Permission> findAllByDeletedIsFalse();
}
