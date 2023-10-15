package com.sky.controller.user;


import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "收货地址")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    @ApiOperation("用户地址查询")
    public Result<List<AddressBook>> list() {
        List<AddressBook> list = addressService.list();
        return Result.success(list);
    }

    @GetMapping("/default")
    @ApiOperation("用户默认地址查询")
    public Result<AddressBook> userDefaultAddress() {
        AddressBook addressBook = addressService.userDefaultAddress();
        return Result.success(addressBook);
    }

    @PostMapping
    @ApiOperation("用户地址新增")
    public Result addUserAddress(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressService.addUserAddress(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("用户修改默认地址")
    public Result userSetDefault(@RequestBody AddressBook addressBook) {
        addressService.userSetDefault(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("用户根据地址id查询")
    public Result<AddressBook> addUserAddress(@PathVariable Long id) {
        AddressBook addressBook = addressService.getById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    @ApiOperation("用户修改地址")
    public Result userUpdataAddress(@RequestBody AddressBook addressBook) {
        addressService.userUpdataAddress(addressBook);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("用户删除地址")
    public Result userDeleteAddress(@RequestBody AddressBook addressBook) {
        addressService.userDeleteAddress(addressBook);
        return Result.success();
    }

}
