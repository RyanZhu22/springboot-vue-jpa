package com.example.springboot_restful.service.impl;

import com.example.springboot_restful.entity.Files;
import com.example.springboot_restful.repository.FilesRepository;
import com.example.springboot_restful.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilesServiceImpl implements FilesService {

    private final FilesRepository filesRepository;

    @Autowired
    public FilesServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public Files save(Files files) {
        return filesRepository.save(files);
    }

    @Override
    public Long count() {
        return filesRepository.count();
    }

    @Override
    public List<Files> findAll() {
        return filesRepository.findAll();
    }

    @Override
    public List<Files> findByMd5(String md5) {
        return filesRepository.findByMd5(md5);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Files> optionalFile = filesRepository.findById(id);
        if (optionalFile.isEmpty()) {
            throw new RuntimeException("File not found with id: " + id);
        }
        Files file = optionalFile.get();
        file.setDeleted(true);
        filesRepository.save(file);
    }

    @Override
    public List<Files> findPage(Integer pageNum, Integer pageSize) {
        Page<Files> page = filesRepository.findAll(PageRequest.of(pageNum, pageSize));
        return page.getContent();
    }


    @Override
    public int deleteBatch(List<Integer> ids) {
        int deletedCount = 0;
        for (Integer id : ids) {
            Optional<Files> optionalFile = filesRepository.findById(id);
            if (optionalFile.isPresent()) {
                Files file = optionalFile.get();
                file.setDeleted(true);
                filesRepository.save(file);
                deletedCount++;
            }
        }
        return deletedCount;
    }

    @Override
    public Files updateEnable(Integer id, Boolean enable) {
        Files file = filesRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id: " + id));
        file.setEnable(enable);
        return filesRepository.save(file);
    }
}
