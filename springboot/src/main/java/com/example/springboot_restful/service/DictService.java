package com.example.springboot_restful.service;


import com.example.springboot_restful.entity.Dict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DictService {

    Dict create(Dict dict);

    Dict getById(Integer id);

    Dict update(Dict dict);

    void delete(Integer id);
    List<Dict> getAll();

    List<Dict> findIcons();

    Page<Dict> findByConditionsWithPagination(Pageable pageable, String code);
}
