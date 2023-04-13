package com.example.springboot_restful.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"auth", "deleted"}, name = "a_d_index"),
        @UniqueConstraint(columnNames = {"path", "page", "deleted"}, name = "p_p_d_index")
    })
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "path", length = 255)
    private String path;

    @Column(name = "orders")
    private String orders;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "page", length = 255)
    private String page;

    @Column(name = "auth", length = 255)
    private String auth;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "type", columnDefinition = "int default 0")
    private Integer type;

    @Column(name = "deleted", columnDefinition = "int default 0")
    private Integer deleted;

    @Column(name = "hide", columnDefinition = "tinyint default 0")
    private Boolean hide;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "update_time")
    private LocalDateTime update_time;

    @Transient
    private List<Permission> children;
}
