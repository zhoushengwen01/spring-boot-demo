package com.example.controller;

import com.example.exceptionHandling.CustomException;
import com.example.pojo.User;
import com.example.response.CommonCode;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @GetMapping("/findUserById/{id}")
    public Object findUserById(@PathVariable("id") String id) {
        return userService.selectUserByid(id);
    }

    @PostMapping("/saveOrUpdateUser")
    public Object saveOrUpdateUser(@RequestBody User user) {
        return userService.saveOrUpdateUser(user);
    }


    @RequestMapping("/testCustomException")
    public Object testCustomException() {
        if (true)
            throw new CustomException(CommonCode.FORESEE_EXCEPTION);
        return null;
    }

    @RequestMapping("/notForesee")
    public Object cannotForesee() {
        int a = 1 / 0;
        return null;
    }


}
