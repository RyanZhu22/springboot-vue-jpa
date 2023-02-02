package com.example.springboot_restful.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_file")
public class Files {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String file_name;
    private String file_type;
    private Long file_size;
    private String url;
    private String md5;
    private Boolean is_delete;
    private Boolean enable;
}
