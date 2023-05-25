package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictRepository extends JpaRepository<Dict, Integer> {

    @Query("SELECT d FROM Dict d WHERE d.type = :type AND d.deleted = false ")
    List<Dict> findIcons(String type);
}

