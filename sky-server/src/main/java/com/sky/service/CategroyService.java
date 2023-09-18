package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategroyService {

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategroy(CategoryDTO categoryDTO);

    void changeCategoryStatus(Integer status, Long id);

    void updateCategoryInfo(CategoryDTO categoryDTO);

    void deleteCategoryInfo(Long id);

    List<Category> list(Integer type);
}
