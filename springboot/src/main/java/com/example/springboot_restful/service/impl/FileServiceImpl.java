package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Files;
import com.example.springboot_restful.mapper.FileMapper;
import com.example.springboot_restful.service.FileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public int insert(Files files) {
        return fileMapper.insert(files);
    }

    @Override
    public int findAll() {
        return fileMapper.findAll();
    }

    @Override
    public List<Files> selectByMd5(String md5) {
        return fileMapper.selectByMd5(md5);
    }

    @Override
    public int deleteByF(Integer id) {
        return fileMapper.deleteByF(id);
    }

    @Override
    public List<Files> findPage(Integer pageNum, Integer pageSize) {
        return fileMapper.findPage(pageNum, pageSize);
    }

    @Override
    public int deleteBatch(List<Integer> ids) {
        return 0;
    }

    @Override
    public int deleteBatchByF(List<Integer> ids) {
        return 0;
    }

    @Override
    public int updateEnable(Files files) {
        return 0;
    }

    /**
     * 需要修改
     * @param ids
     * @return
     */
//    @Override
//    public int deleteBatch(List<Integer> ids) {
//        // 判断ids不为null
//        if (ids == null || ids.size() == 0) {
//            throw new MsgException("500", "ids为null");
//        }
//        try {
//            return fileMapper.deleteBatch(ids);
//        } catch (Exception e) {
//            throw new MsgException("500", "Server error");
//        }
//    }

    /**
     * 需要修改
     * @param files
     * @return
     */
//    @Override
//    public int deleteBatchByF(List<Integer> ids) {
//        int i = fileMapper.deleteBatchByF(ids);
//        if (i != 1) {
//            throw new MsgException("500", "Server error");
//        }
//        return i;
//    }

    /**
     * 需要修改
     * @param files
     * @return
     */
//    @Override
//    public int updateEnable(Files files) {
//        int i = fileMapper.updateEnable(files);
//        if (i != 1) {
//            throw new MsgException("500", "Server error");
//        }
//        return i;
//    }
}
