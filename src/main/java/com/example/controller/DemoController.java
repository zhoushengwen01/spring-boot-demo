package com.example.controller;

import com.example.dao.UserDao;
import com.example.exceptionHandling.CustomException;
import com.example.pojo.Dictionaries;
import com.example.pojo.User;
import com.example.response.CommonCode;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

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


    @RequestMapping("/createDictionaties")
    public void createDictionaties(String count) {
        generate(count);
    }

    @Autowired
    UserDao dao;


    //密码可能会包含的字符集合
    private static char[] fullCharSource = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '{', '}', '|', ':', '"', '<', '>', '?', ';', '\'', ',', '.', '/', '-', '=', '`'};
    //将可能的密码集合长度
    private static BigInteger fullCharLength = new BigInteger(String.valueOf(fullCharSource.length));

    //maxLength：生成的字符串的最大长度
    public void generate(String cc) {
        int maxLength = 6;
        //计数器，多线程时可以对其加锁，当然得先转换成Integer类型。
        BigInteger counter = new BigInteger(cc);

        int c = 0;


        StringBuilder buider = new StringBuilder();

        List<Dictionaries> datas = new LinkedList<>();

        while (buider.toString().length() <= maxLength) {


            buider = new StringBuilder(maxLength * 2);
            BigInteger _counter = new BigInteger(counter.toString());
            //10进制转换成26进制
            while (-1 != _counter.compareTo(fullCharLength)) {

                BigInteger[] b = _counter.divideAndRemainder(fullCharLength);//[整数,余数]
                //获得低位
                buider.insert(0, fullCharSource[b[1].intValue()]);
                _counter = b[0];
                //处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
                _counter = _counter.subtract(new BigInteger("-1"));
            }
            //最高位
            buider.insert(0, fullCharSource[_counter.intValue()]);
            counter = counter.add(new BigInteger("1"));

            c++;
            Dictionaries dictionaries = new Dictionaries(counter.toString(), buider.toString());
            datas.add(dictionaries);
            if (c == 100) {
                logger.info(dictionaries.getId());
                dao.insertData(datas);
                datas.clear();
                c = 0;
            }
        }
    }


}
