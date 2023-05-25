package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.repository.DictRepository;
import com.example.springboot_restful.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final DictRepository dictRepository;

    @Override
//    @Cacheable(value = "findIcons")
    public List<Dict> findIcons() {
        List<Dict> all = dictRepository.findAll();
        System.out.println(all);
        List<Dict> icon = dictRepository.findIcons("icon");
        System.out.println(icon);
        return all;
    }
}
