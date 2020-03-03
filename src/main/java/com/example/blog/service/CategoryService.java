package com.example.blog.service;

import com.example.blog.bean.Category;
import com.example.blog.bean.response.PageResult;
import com.example.blog.bean.response.Result;
import com.example.blog.bean.response.ResultCode;
import com.example.blog.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Result add(Category category) {
        Category save = categoryRepository.save(category);
        if (save == null) {
            throw new RuntimeException();
        }
        return new Result(ResultCode.SUCCESS, save);
    }

    public Result findAll(Integer pageNum, Integer pageSize, String cateName) {
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Category> all;
        if (StringUtils.isEmpty(cateName)) {  // not have condition
            all = categoryRepository.findAll(of);
        } else
            all = categoryRepository.findByCateNameContaining(cateName, of);
        return new Result(ResultCode.SUCCESS, new PageResult<>(all.getTotalElements(), all.getContent()));
    }

    public Result update(Category category) {
        Category save = categoryRepository.save(category);
        if (save == null) {
            throw new RuntimeException();
        }
        return new Result(ResultCode.SUCCESS, save);
    }

    public Result delete(String id) {
        categoryRepository.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    public Result batchDelete(List<String> ids) {
        categoryRepository.deleteByIdIn(ids);
        return new Result(ResultCode.SUCCESS);
    }

}
