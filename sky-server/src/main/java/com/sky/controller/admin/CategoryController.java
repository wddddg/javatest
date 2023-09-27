package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategroy(@RequestBody CategoryDTO categoryDTO) {
        categoryService.addCategroy(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改分类状态")
    public Result changeCategoryStatus(@PathVariable Integer status, Long id) {
        categoryService.changeCategoryStatus(status, id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改分类信息")
    public Result updateCategoryInfo(@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategoryInfo(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除分类信息")
    public Result deleteCategoryInfo(Long id) {
        categoryService.deleteCategoryInfo(id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
