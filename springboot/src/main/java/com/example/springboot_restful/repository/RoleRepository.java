package com.example.springboot_restful.repository;


import com.example.springboot_restful.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByFlag(String flag);
}
