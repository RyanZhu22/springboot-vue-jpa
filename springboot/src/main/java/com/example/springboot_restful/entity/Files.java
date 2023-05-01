package com.example.springboot_restful.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean deleted;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean enable;
}
