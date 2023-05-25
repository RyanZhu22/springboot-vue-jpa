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

    @Column
    private String name;

    @Column
    private String path;

    @Column
    private String orders;

    @Column
    private String icon;

    @Column
    private String page;

    @Column
    private String auth;

    @Column
    private Integer pid;

    @Column(columnDefinition = "int default 0")
    private Integer type;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean deleted = false;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean hide = false;

    @Column(updatable = false)
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;

    @Transient
    private List<Permission> children;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
