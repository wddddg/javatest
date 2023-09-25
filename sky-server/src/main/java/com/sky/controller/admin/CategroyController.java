package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategroyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类接口")
public class CategroyController {

    @Autowired
    CategroyService categroyService;

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categroyService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategroy(@RequestBody CategoryDTO categoryDTO) {
        categroyService.addCategroy(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改分类状态")
    public Result changeCategoryStatus(@PathVariable Integer status, Long id) {
        categroyService.changeCategoryStatus(status, id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改分类信息")
    public Result updateCategoryInfo(@RequestBody CategoryDTO categoryDTO) {
        categroyService.updateCategoryInfo(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除分类信息")
    public Result deleteCategoryInfo(Long id) {
        categroyService.deleteCategoryInfo(id);
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
        List<Category> list = categroyService.list(type);
        return Result.success(list);
    }

}
