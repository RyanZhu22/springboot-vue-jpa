package com.example.springboot_restful.mapper;

import com.example.springboot_restful.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper{

    @Select("SELECT * from sys_user where deleted = 0")
    List<User> findAll();


    @Insert("INSERT into sys_user(username,password,nickname,email,phone,address) VALUES(#{username},#{password}," +
            "#{nickname},#{email},#{phone},#{address})")
    int insert(User user);

    @Select("SELECT * from sys_user where username = #{username}")
    User findByUsername(@Param("username") String username);

    User findByUserId(@Param("id") Integer id);

    User login(@Param("username") String username, @Param("password") String password);

    User registerByUsername(@Param("username") String username);

    // 真删除
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    int deleteByIdT(@Param("id") Integer id);

    // 假删除
    int deleteByIdF(@Param("id") Integer id);

    int update(User user);

    @Select("SELECT * from sys_user where username like concat('%', #{username}, '%') " +
            "and email like concat('%', #{email}, '%') " +
            "and address like concat('%', #{address}, '%') " +
            "and deleted like concat('%', #{deleted}, '%') " +
            "limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username, String email, String address, Integer deleted);

    // 查询总条数
    @Select("SELECT count(*) from sys_user where username like concat('%', #{username}, '%')" +
            "and email like concat('%', #{email}, '%') " +
            "and address like concat('%', #{address}, '%')" +
            "and deleted like concat('%', #{deleted}, '%')")
    Integer selectTotal(String username, String email, String address, Integer deleted);

    // 插入（批量）
    int saveBatch(List<User> list);
}
