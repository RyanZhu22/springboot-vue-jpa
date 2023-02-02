package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    int insert(Files files);
}
