package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.mapper.DictMapper;
import com.example.springboot_restful.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;


    @Override
    public List<Dict> findIcon() {
        return dictMapper.findIcon();
    }
}
