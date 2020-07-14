package com.example.blog.service;

import com.example.blog.dao.CategoryDao;
import com.example.blog.entity.Category;
import com.example.blog.entity.response.PageResult;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import com.example.blog.utils.IdWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    CategoryDao categoryDao;

    public Result add(Category category) {
        category.setId(IdWorker.genId());
        Category save = categoryDao.save(category);
        return new Result(ResultCode.SUCCESS, save);
    }

    public Result findAll(Integer pageNum, Integer pageSize, String cateName) {
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Category> all;
        if (StringUtils.isEmpty(cateName)) {  // not have condition
            all = categoryDao.findAll(of);
        } else
            all = categoryDao.findByCateNameContaining(cateName, of);
        return new Result(ResultCode.SUCCESS, new PageResult<>(all.getTotalElements(), all.getContent()));
    }

    public Result update(Category category) {
        Category save = categoryDao.save(category);
        return new Result(ResultCode.SUCCESS, save);
    }

    public Result delete(String id) {
        categoryDao.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    public Result batchDelete(List<String> ids) {
        categoryDao.deleteByIdIn(ids);
        return new Result(ResultCode.SUCCESS);
    }

}
