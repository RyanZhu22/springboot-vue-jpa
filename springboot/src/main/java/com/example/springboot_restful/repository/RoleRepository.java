package com.example.springboot_restful.repository;


import com.example.springboot_restful.entity.Role;
import com.example.springboot_restful.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Role r SET r.deleted = true WHERE r.id = :id")
    void updateDeletedById(@Param("id") Integer id);

    Role findByFlag(String flag);

    @Query("SELECT r FROM Role r " +
        "WHERE (r.name LIKE CONCAT('%',:name,'%')) " +
        "AND (r.deleted = false)")
    Page<Role> findByConditionsWithPagination(Pageable pageable, String name);
}
