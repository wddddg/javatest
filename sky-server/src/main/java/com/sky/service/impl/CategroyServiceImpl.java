package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.mapper.CategroyMapper;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.CategroyService;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategroyServiceImpl implements CategroyService {

    @Autowired
    private CategroyMapper categroyMapper;


    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Employee> page = categroyMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }


    public void addCategroy(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.ENABLE);

        categroyMapper.addCategroy(category);
    }

    public void changeCategoryStatus(Integer status, Long id) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        categroyMapper.update(category);
    }


    public void updateCategoryInfo(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        categroyMapper.update(category);
    }


    public void deleteCategoryInfo(Long id) {
        categroyMapper.delete(id);
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    public List<Category> list(Integer type) {
        return categroyMapper.list(type);
    }


}
