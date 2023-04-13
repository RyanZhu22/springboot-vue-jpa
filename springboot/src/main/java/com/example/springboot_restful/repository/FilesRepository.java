package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer> {

    Files findByMd5(String md5);
}
