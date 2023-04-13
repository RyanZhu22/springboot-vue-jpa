package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Files;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FilesService {

    Files save(Files files);

    Long count();

    List<Files> findAll();

    Files findByMd5(String md5);

    void deleteById(Integer id);

    List<Files> findPage(Integer pageNum, Integer pageSize);

    int deleteBatch(List<Integer> ids);

    Files updateEnable(Integer id, Boolean enable);
}
