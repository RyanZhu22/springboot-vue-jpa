package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper{

    @Select("SELECT * from sys_user")
    List<User> findAll();

    @Insert("INSERT into sys_user(username,password,nickname,email,phone,address) VALUES(#{username},#{password}," +
            "#{nickname},#{email},#{phone},#{address})")
    int insert(User user);

    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    int update(User user);

    @Select("SELECT * from sys_user where username like concat('%', #{username}, '%') " +
            "and email like concat('%', #{email}, '%') " +
            "and address like concat('%', #{address}, '%') limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username, String email, String address);

    // 查询总条数
    @Select("SELECT count(*) from sys_user where username like concat('%', #{username}, '%')" +
            "and email like concat('%', #{email}, '%') " +
            "and address like concat('%', #{address}, '%')")
    Integer selectTotal(String username, String email, String address);
}
