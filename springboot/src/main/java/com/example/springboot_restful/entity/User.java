package com.example.springboot_restful.entity;


import com.example.springboot_restful.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "uid", length = 40)
    private String uid;

    @Column(name = "phone", length = 40)
    private String phone;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "address", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;

    @Column(name = "deleted", columnDefinition = "tinyint default 0")
    private Integer deleted;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "update_time")
    private LocalDateTime update_time;

    @PrePersist
    public void onCreate() {
        create_time = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        update_time = LocalDateTime.now();
    }

    public void updateFields(User updatedUser) {
        this.username = updatedUser.username;
        this.password = updatedUser.password;
        this.name = updatedUser.name;
        this.email = updatedUser.email;
        this.uid = updatedUser.uid;
        this.phone = updatedUser.phone;
        this.avatar = updatedUser.avatar;
        this.address = updatedUser.address;
        this.role = updatedUser.role;
        this.deleted = updatedUser.deleted;
        this.create_time = updatedUser.create_time;
        this.update_time = updatedUser.update_time;
    }

    // implements UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
