package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategroyService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品接口")
public class DishController {

    private static final String KEY_NAME = "dish_";

    @Autowired
    DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @GetMapping("/page")
//    @ApiOperation("分类分页查询")
//    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
//        PageResult pageResult = categroyService.pageQuery(categoryPageQueryDTO);
//        return Result.success(pageResult);
//    }

    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        dishService.addDish(dishDTO);

        String key = KEY_NAME + dishDTO.getCategoryId();
        redisTemplate.delete(key);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> queryDish(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.queryDish(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result deleteBatch(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        cleanCache(KEY_NAME + "*");
        return Result.success();
    }



    @PostMapping("/status/{status}")
    @ApiOperation("菜品状态修改")
    public Result changeStatus(@PathVariable Integer status, Long id) {
        dishService.changeStatus(status, id);
        cleanCache(KEY_NAME + "*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("菜品id查询")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("菜品状态修改")
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        cleanCache(KEY_NAME + "*");
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    private void cleanCache(String keyName) {
        Set keys = redisTemplate.keys(keyName);
        redisTemplate.delete(keys);
    }

}
