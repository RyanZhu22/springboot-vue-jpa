package com.example.springboot_restful.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "dict",
        uniqueConstraints = @UniqueConstraint(columnNames = {"code", "deleted"}))
public class Dict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;


    @Column(name = "type")
    private String type;

    @Column(name = "deleted", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean deleted = false;
}
