package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("购物车新增商品")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.add(shoppingCartDTO);
        return  Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车查询")
    public Result<List<ShoppingCart>> queryUserList() {
        List<ShoppingCart> list = shoppingCartService.queryUserList();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("购物车清空")
    public Result clean() {
        shoppingCartService.clean();
        return Result.success();
    }

}
