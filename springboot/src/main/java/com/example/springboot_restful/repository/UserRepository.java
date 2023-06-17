package com.example.springboot_restful.repository;

import com.example.springboot_restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User findByUid(String uid);

    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.id = :id")
    void updateDeleted(Integer id);

    @Query("SELECT u FROM User u " +
        "WHERE (u.username LIKE CONCAT('%',:username,'%')) " +
        "AND (u.email LIKE CONCAT('%',:email,'%')) " +
        "AND (u.address LIKE CONCAT('%',:address,'%')) " +
        "AND (u.deleted = false)")
    List<User> findByConditions(String username,
                                String email,
                                String address);

    @Query("SELECT u FROM User u " +
        "WHERE (u.username LIKE CONCAT('%',:username,'%')) " +
        "AND (u.email LIKE CONCAT('%',:email,'%')) " +
        "AND (u.address LIKE CONCAT('%',:address,'%')) " +
        "AND (u.deleted = false)")
    Page<User> findByConditionsWithPagination(Pageable pageable,
                                              String username,
                                              String email,
                                              String address);

    @Query("SELECT COUNT(u) FROM User u " +
        "WHERE (:username IS NULL OR u.username = :username) " +
        "AND (:email IS NULL OR u.email = :email) " +
        "AND (:address IS NULL OR u.address = :address) " +
        "AND (:deleted IS NULL OR u.deleted = :deleted)")
    long countByConditions(String username, String email, String address, Integer deleted);
}
