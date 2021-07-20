package com.example.dao;

import com.example.pojo.Dictionaries;
import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserDao {
    User findUserById(@Param("id") String id);

    int saveUser(User user);

    int updateUser(User user);


    int insertData(List<Dictionaries> datas);

    String getMaxId();

    String getTableSize();
}
