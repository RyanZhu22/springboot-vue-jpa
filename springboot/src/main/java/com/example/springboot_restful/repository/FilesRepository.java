package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer> {

    List<Files> findByMd5(String md5);

    @Modifying
    @Transactional
    @Query("UPDATE Files f SET f.enable = :enable WHERE f.id = :id")
    void updateEnable(Integer id, Boolean enable);
}
