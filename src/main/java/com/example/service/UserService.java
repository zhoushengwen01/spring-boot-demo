package com.example.service;

import com.example.pojo.User;
import com.example.response.ResponseResult;

public interface UserService {
    ResponseResult selectUserByid(String id);

    ResponseResult saveOrUpdateUser(User user);
}
