package com.example.springboot_restful.controller;

import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.common.error.CommonEnum;
import com.example.springboot_restful.entity.Dict;
import com.example.springboot_restful.model.dto.dict.DictCreateDto;
import com.example.springboot_restful.model.dto.dict.DictUpdateDto;
import com.example.springboot_restful.model.page.DictPage;
import com.example.springboot_restful.service.DictService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping
    public ResultBody create(@Valid @RequestBody DictCreateDto dictCreateDto) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictCreateDto, dict);
        return ResultBody.success(dictService.create(dict));
    }

    @GetMapping("/{id}")
    public ResultBody get(@PathVariable Integer id) {
        Dict dict = dictService.getById(id);
        if (dict == null) {
            return ResultBody.error(CommonEnum.NOT_FOUND.getResultCode(), "Dict not found");
        }
        return ResultBody.success(dict);
    }

    @PutMapping("/{id}")
    public ResultBody update(@PathVariable Integer id, @Valid @RequestBody DictUpdateDto dictUpdateDto) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictUpdateDto, dict);
        dict.setId(id);
        Dict updatedDict = dictService.update(dict);
        if (updatedDict == null) {
            return ResultBody.error(CommonEnum.NOT_FOUND.getResultCode(), "Dict not found");
        }
        return ResultBody.success(updatedDict);
    }

    @DeleteMapping("/{id}")
    public ResultBody delete(@PathVariable Integer id) {
        dictService.delete(id);
        return ResultBody.success();
    }

    @GetMapping
    public ResultBody getAll() {
        return ResultBody.success(dictService.getAll());
    }

    /**
     * page query + code query query interface
     *
     * @param dictPage
     * @return
     */
    @GetMapping("/page")
    public ResultBody findPage(DictPage dictPage) {
        PageRequest pageable = PageRequest.of(dictPage.getPage() - 1, dictPage.getSize());
        Page<Dict> data = dictService.findByConditionsWithPagination(pageable, dictPage.getCode());
        return ResultBody.success(data);
    }
}