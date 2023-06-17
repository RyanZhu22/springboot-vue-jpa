package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.common.error.ResultEnum;
import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.entity.Permission;
import com.example.springboot_restful.exception.BusinessException;
import com.example.springboot_restful.repository.DictRepository;
import com.example.springboot_restful.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public Dict create(Dict dict) {
        return dictRepository.save(dict);
    }

    @Override
    public Dict getById(Integer id) {
        Optional<Dict> optionalDict = dictRepository.findById(id);
        return optionalDict.orElse(null);
    }

    @Override
    public Dict update(Dict dict) {
        Optional<Dict> optPermission = dictRepository.findById(dict.getId());
        if (optPermission.isEmpty()) {
            throw new BusinessException(ResultEnum.NOT_EXIST);
        }
        return dictRepository.save(dict);
    }

    @Override
    public void delete(Integer id) {
        if(dictRepository.existsById(id)) {
            dictRepository.deleteById(id);
        }
    }

    @Override
    public List<Dict> getAll() {
        return dictRepository.findAll();
    }

    @Override
//    @Cacheable(value = "findIcons")
    public List<Dict> findIcons() {
        List<Dict> all = dictRepository.findAll();
        System.out.println(all);
        List<Dict> icon = dictRepository.findIcons("icon");
        System.out.println(icon);
        return all;
    }

    @Override
    public Page<Dict> findByConditionsWithPagination(Pageable pageable, String code) {
        return dictRepository.findByConditionsWithPagination(pageable, code);
    }
}
