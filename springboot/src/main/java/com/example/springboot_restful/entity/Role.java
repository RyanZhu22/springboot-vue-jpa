package com.example.springboot_restful.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role",
    uniqueConstraints = @UniqueConstraint(columnNames = {"flag", "deleted"}, name = "flag_deleted_index"))
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private String name;

    @Column
    private String flag;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean deleted;

    @Column(updatable = false)
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;

    @Transient
    private List<Integer> permissionIds;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
