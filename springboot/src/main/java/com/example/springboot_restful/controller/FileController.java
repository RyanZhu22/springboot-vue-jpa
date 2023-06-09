package com.example.springboot_restful.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot_restful.common.ResultBody;
import com.example.springboot_restful.entity.Files;
import com.example.springboot_restful.model.dto.files.FilesUpdateDto;
import com.example.springboot_restful.model.page.FilePage;
import com.example.springboot_restful.service.FilesService;
import com.example.springboot_restful.service.FilesService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件接口
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private FilesService filesService;

    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        // 先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);

        // 判断配置文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadParentFile.exists()) {
            uploadParentFile.mkdirs();
        }
        // 定义文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);

        String url;
        // 存进磁盘目录
        file.transferTo(uploadFile);
        // 获取MD5
        String md5 = SecureUtil.md5(uploadFile);
        // 查询数据库是否存在相同md5
        List<Files> dbFiles = filesService.findByMd5(md5);
        if (dbFiles.size() > 1) {
            // Delete all files except the first one
            for (int i = 1; i < dbFiles.size(); i++) {
                Files fileToDelete = dbFiles.get(i);
                url = fileToDelete.getUrl();
                // md5存在，删除磁盘上传文件
                uploadFile.delete();
            }
        }
        // 重复md5不存在 直接获取
        url = "http://localhost:8080/file/" + fileUuid;


        // 存储数据库
        Files saveFile = new Files();
        saveFile.setFileName(originalFilename);
        saveFile.setFileType(type);
        saveFile.setFileSize(size / 1024);
        saveFile.setMd5(md5);
        saveFile.setUrl(url);
        saveFile.setDeleted(false);
        saveFile.setHide(true);
        filesService.save(saveFile);
        return url;
    }

    /**
     * 文件下载接口
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 假 删除文件接口
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBody delete(@PathVariable Integer id) {
        filesService.deleteById(id);
        return ResultBody.success();
    }

    /**
     * 批量删除文件接口
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public ResultBody deleteBatchByF(@RequestBody List<Integer> ids) {
        return ResultBody.success();
    }

    @PostMapping("/update")
    public ResultBody updateEnable(@Valid @RequestBody FilesUpdateDto filesUpdateDto) {
        filesService.updateEnable(filesUpdateDto.getId(), filesUpdateDto.getHide());
        return ResultBody.success();
    }

    /**
     * 文件分页查询接口
     *
     * @param filePage
     * @return
     */
    @GetMapping("/page")
    public ResultBody findPage(FilePage filePage) {
        // 查询所有数据
        Long total = filesService.count();
        // 查询相关页数的数据
        List<Files> filesList = filesService.findPage(filePage.getPage() -1, filePage.getSize());
        // map格式放回数据
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("data", filesList);
        return ResultBody.success(res);
    }

}
