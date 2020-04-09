package com.example.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hello world");
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String amdin = bCryptPasswordEncoder.encode("admin");
        System.out.println("amdin = " + amdin);
    }

}
