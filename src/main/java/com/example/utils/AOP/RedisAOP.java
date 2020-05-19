package com.example.utils.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * @ClassName RedisAOP
 * @description: redis 切面缓存
 **/
@Aspect
@Slf4j
@Component
public class RedisAOP {


    //查询
    private static final Pattern GET_CACHE_PATTERN = Pattern.compile("^((get)|(query)|(select)|(list)|(find)).*$");

    //增删改
    private static final Pattern SET_CACHE_PATTERN = Pattern.compile("^((add)|(insert)|(save)|(batchInsert)|(batchUpdate)|(update)|(delete)|(remove)).*$");


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 定义切点为缓存注解
     */
    @Pointcut("@within(com.example.utils.AOP.RedisCache)")
    public void queryCachePointcut() {
    }

    @Around("queryCachePointcut()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws Throwable {


        //获取目标方法所在类
        String className = joinPoint.getTarget().toString().split("@")[0];
        //获取目标方法的方法名称
        String methodName = joinPoint.getSignature().getName();


        //查询方法
        if (GET_CACHE_PATTERN.matcher(methodName).matches()) {

            //获取查询条件参数
            String args = getArgs(joinPoint);

            //redis中key格式：
            String redisKey = className + "." + methodName + args;

            Object obj = null;
            if (redisTemplate.hasKey(redisKey)) {
                log.info("**********从Redis中查到了数据**********");
                log.info("Redis的KEY值:" + redisKey);
                String value = String.valueOf(redisTemplate.opsForValue().get(redisKey));
                //字符转对象
                obj = strToObject(value, getReturnType(joinPoint));
            } else {
                log.info("**********没有从Redis查到数据,开始从MySQL查询数据**********");
                obj = joinPoint.proceed();
                redisTemplate.opsForValue().set(redisKey, objectToStr(obj));
            }
            return obj;
        }

        //增删改
        if (SET_CACHE_PATTERN.matcher(methodName).matches()) {
            //清楚包含所有此全路径类名称的key值
            Set<String> keys = redisTemplate.keys(className + "*");
            redisTemplate.delete(keys);
            return joinPoint.proceed();
        }

        return joinPoint.proceed();
    }

    //获取切入方法返回值类型
    private Class<?> getReturnType(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Type type = method.getGenericReturnType();
        return (Class) type;
    }


    //获取切入点参数
    private String getArgs(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder argsStr = new StringBuilder("(");
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (i == 0) {
                argsStr.append(String.valueOf(arg));
            } else {
                argsStr.append("," + String.valueOf(arg));
            }
        }
        argsStr.append(")");
        return argsStr.toString();
    }

    //字符转对象
    private <T> T strToObject(String str, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(str, clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    //对象转字符
    private <T> String objectToStr(T t) {
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
