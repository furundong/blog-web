package com.example.blog.dao;

import com.example.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryDao extends JpaRepository<Category,String> {

    //static final List<Class<?>> TYPES = Arrays.asList(Pageable.class, Sort.class);
    Page<Category> findByCateNameContaining(String cateName, Pageable pageable); //this need use Pageable Interface


    @Modifying
    @Transactional
    void deleteByIdIn(List<String> ids);
}
