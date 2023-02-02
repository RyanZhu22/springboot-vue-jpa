package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Files;
import com.example.springboot_restful.mapper.FileMapper;
import com.example.springboot_restful.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public int insert(Files files) {
        return fileMapper.insert(files);
    }
}
