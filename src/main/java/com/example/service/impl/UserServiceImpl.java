package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.pojo.User;
import com.example.response.CommonCode;
import com.example.response.ResponseResult;
import com.example.service.UserService;
import com.example.utils.AOP.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;

@Service
@RedisCache
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;


    @Override
    public ResponseResult selectUserByid(String id) {
        User user = userDao.findUserById(id);
        if (user != null)
            return new ResponseResult(CommonCode.SUCCESS, user);
        return new ResponseResult(CommonCode.FAIL);
    }

    @Override
    public ResponseResult saveOrUpdateUser(User user) {
        int result = -1;
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(UUID.randomUUID().toString());
            result = userDao.saveUser(user);
        } else {
            result = userDao.updateUser(user);
        }
        if (result == 1)
            return new ResponseResult(CommonCode.SUCCESS);
        return new ResponseResult(CommonCode.FAIL);
    }
}
