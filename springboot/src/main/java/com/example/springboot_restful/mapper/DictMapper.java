package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictMapper {

    List<Dict> findIcon();
}
