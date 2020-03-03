package com.example.blog.controller;

import com.example.blog.bean.Category;
import com.example.blog.bean.response.Result;
import com.example.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PutMapping
    public Result updCategory(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @PostMapping
    public Result addCategory(@RequestBody Category category) {
        return categoryService.add(category);
    }

    @DeleteMapping("/{id}")
    public Result delCategory(@PathVariable("id") String id){
        return categoryService.delete(id);
    }

    @DeleteMapping("/batchDelete")
    public Result batchDeleteCategory(String[] ids){
        return categoryService.batchDelete(Arrays.asList(ids));
    }

    @GetMapping("/getData")
    public Result getData(Integer pageNum, Integer pageSize,String cateName) {
        return categoryService.findAll(pageNum, pageSize,cateName);
    }

}
