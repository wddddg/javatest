package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorsMapper dishFlavorssMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.addCategroy(dish);

        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && flavors.size() > 0 ) {
            flavors.forEach(item -> {
                item.setDishId(dishId);
            });
            dishFlavorssMapper.insertBatch(flavors);
        }

    }


    public PageResult queryDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.queryDish(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIdsByDishIds != null && setmealIdsByDishIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        dishMapper.deleteBatch(ids);
        dishFlavorssMapper.deleteBatch(ids);
    }


    public void changeStatus(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
//        if (status == StatusConstant.DISABLE) {
//            // 如果是停售操作，还需要将包含当前菜品的套餐也停售
//            List<Long> dishIds = new ArrayList<>();
//            dishIds.add(id);
//            // select setmeal_id from setmeal_dish where dish_id in (?,?,?)
//            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
//            if (setmealIds != null && setmealIds.size() > 0) {
//                for (Long setmealId : setmealIds) {
//                    Setmeal setmeal = Setmeal.builder()
//                            .id(setmealId)
//                            .status(StatusConstant.DISABLE)
//                            .build();
//                    setmealMapper.update(setmeal);
//                }
//            }
//        }
    }

    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavors = dishFlavorssMapper.getByDishId(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        dishFlavorssMapper.deleteByDishId(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0 ) {
            flavors.forEach(item -> {
                item.setDishId(dishDTO.getId());
            });
            dishFlavorssMapper.insertBatch(flavors);
        }
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> list = dishMapper.listByCategoryId(dish.getCategoryId(), dish.getStatus());
        List<DishVO> voList = new ArrayList<>();
        for (Dish dishItem : list) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dishItem,dishVO);
            List<DishFlavor> flavors = dishFlavorssMapper.getByDishId(dishItem.getId());

            dishVO.setFlavors(flavors);
            voList.add(dishVO);
        }
        return voList;
    }

    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.listByCategoryId(dish.getCategoryId(), dish.getStatus());
    }


}
