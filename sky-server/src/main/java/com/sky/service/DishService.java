package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void addDish(DishDTO dishDTO);

    PageResult queryDish(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    void changeStatus(Integer status, Long id);

    DishVO getById(Long id);

    void update(DishDTO dishDTO);
}
