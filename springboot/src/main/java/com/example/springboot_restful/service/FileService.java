package com.example.springboot_restful.service;

import com.example.springboot_restful.entity.Files;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FileService {

    int insert(Files files);

    int findAll();

    List<Files> selectByMd5(@Param("md5") String md5);

    int deleteByF(@PathVariable Integer id);

    List<Files> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize);

    int deleteBatch(@RequestBody List<Integer> ids);

    int deleteBatchByF(@RequestBody List<Integer> ids);

    int updateEnable(@RequestBody Files files);
}
