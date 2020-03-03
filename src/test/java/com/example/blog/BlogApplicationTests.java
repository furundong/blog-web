package com.example.blog;

import com.example.blog.bean.Category;
import com.example.blog.dao.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class BlogApplicationTests {

    @Autowired
    CategoryRepository categoryRepository;
    @Test
    void contextLoads() {
        List<Category> all = categoryRepository.findAll();
        System.out.println("all = " + all);
    }

}
