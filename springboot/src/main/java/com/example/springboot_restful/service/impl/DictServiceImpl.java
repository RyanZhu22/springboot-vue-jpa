package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.repository.DictRepository;
import com.example.springboot_restful.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    private final DictRepository dictRepository;

    @Autowired
    public DictServiceImpl(DictRepository dictRepository) {
        this.dictRepository = dictRepository;
    }

    @Override
    public List<Dict> findIcon() {
        return dictRepository.findByType("Icon");
    }
}
