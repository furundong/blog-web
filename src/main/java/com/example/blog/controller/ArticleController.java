package com.example.blog.controller;

import com.example.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController<E> {

    @Autowired
    ArticleService articleService;

    @GetMapping("/test")
    public String test() {
        List list =
                articleService.findList();
        System.out.println("list = " + list);
        return "test：“";
    }

}
