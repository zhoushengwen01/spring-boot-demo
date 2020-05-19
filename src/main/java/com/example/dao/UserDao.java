package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserDao {
    User findUserById(@Param("id") String id);

    int saveUser(User user);

    int updateUser(User user);
}
