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

    @Column(name = "file_name", length = 255)
    private String file_name;

    @Column(name = "file_type", length = 255)
    private String file_type;

    @Column(name = "file_size")
    private Long file_size;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "md5", length = 255)
    private String md5;

    @Column(name = "is_delete", columnDefinition = "tinyint(1) unsigned zerofill")
    private Boolean isDelete;

    @Column(name = "enable", columnDefinition = "tinyint(1) unsigned zerofill")
    private Boolean enable;
}
