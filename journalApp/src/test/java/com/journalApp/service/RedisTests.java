package com.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSendMail(){
        redisTemplate.opsForValue().set("email", "gmail@email.com");

        Object email = redisTemplate.opsForValue().get("email");

        System.out.println("Email: " + email);
        Object salary = redisTemplate.opsForValue().get("salary");
        int a = 1;

    }
}
