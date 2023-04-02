package com.example.springboot_restful.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Files {

    private Integer id;
    private String file_name;
    private String file_type;
    private Long file_size;
    private String url;
    private String md5;
    private Boolean is_delete;
    private Boolean enable;
}
