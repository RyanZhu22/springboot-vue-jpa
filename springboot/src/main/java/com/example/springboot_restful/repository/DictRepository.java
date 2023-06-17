package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictRepository extends JpaRepository<Dict, Integer> {

    @Query("SELECT d FROM Dict d WHERE d.type = :type AND d.deleted = false ")
    List<Dict> findIcons(String type);

    @Query("SELECT d FROM Dict d " +
        "WHERE (d.code LIKE CONCAT('%',:code,'%')) " +
        "AND (d.deleted = false)")
    Page<Dict> findByConditionsWithPagination(Pageable pageable, String code);
}

