package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Files;

import java.util.List;

public interface FilesService {

    Files save(Files files);

    Long count();

    List<Files> findAll();

    List<Files> findByMd5(String md5);

    void deleteById(Integer id);

    List<Files> findPage(Integer pageNum, Integer pageSize);

    int deleteBatch(List<Integer> ids);

    void updateEnable(Integer id, Boolean enable);
}
