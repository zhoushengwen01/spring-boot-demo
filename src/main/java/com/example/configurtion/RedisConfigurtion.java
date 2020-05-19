package com.example.configurtion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfigurtion {

    @Autowired
    private RedisTemplate redisTemplate;


    //修改RedisTemplate的序列化策略,解决乱码问题
    @Bean
    public RedisTemplate<String, Object> stringSerializerRedisTemplate() {
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(objectJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        return redisTemplate;
    }


}
