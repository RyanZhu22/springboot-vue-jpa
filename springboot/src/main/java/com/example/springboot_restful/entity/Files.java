package com.example.springboot_restful.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "file")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column
    private String url;

    @Column
    private String md5;

    @Column(name = "deleted", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean deleted = false;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean hide;
}
