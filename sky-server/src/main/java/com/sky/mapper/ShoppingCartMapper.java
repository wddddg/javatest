package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumer(ShoppingCart shoppingCartItem);

    @Insert("insert shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time)" +
            "values (#{})")
    void insert(ShoppingCart shoppingCart);
}
