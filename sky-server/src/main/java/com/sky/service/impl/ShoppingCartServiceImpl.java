package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0) {
            ShoppingCart shoppingCartItem = list.get(0);
            shoppingCartItem.setNumber(shoppingCartItem.getNumber() + 1);
            shoppingCartMapper.updateNumer(shoppingCartItem);
        } else {
            Long dishId = shoppingCart.getDishId();
            String name = "";
            String image = "";
            BigDecimal amount = new BigDecimal(0);
            if (dishId != null) {
                Dish dish = dishMapper.getById(dishId);
                name = dish.getName();
                image = dish.getImage();
                amount = dish.getPrice();
            } else {
                Long setmealId = shoppingCart.getSetmealId();

                Setmeal setmeal = setmealMapper.getById(setmealId);
                name = setmeal.getName();
                image = setmeal.getImage();
                amount = setmeal.getPrice();
            }
            shoppingCart.setName(name);
            shoppingCart.setImage(image);
            shoppingCart.setAmount(amount);
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);

        }



    }

    @Override
    public List<ShoppingCart> queryUserList() {
        Long id = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(id).build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }
}
