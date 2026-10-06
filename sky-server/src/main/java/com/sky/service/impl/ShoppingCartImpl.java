package com.sky.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.service.ShoppingCartService;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ShoppingCartImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        // 只能查询自己的购物车
        shoppingCart.setUserId(BaseContext.getCurrentId());

        //判断当前商品是否在购物车中
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list != null && list.size() > 0){
            // 当前商品在购物车内
            shoppingCart= list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.update(shoppingCart);
        }else{
            // 当前商品不在购物车内
            Long dishId = shoppingCartDTO.getDishId();
            Long setmealId = shoppingCartDTO.getSetmealId();
            if(dishId != null){
                Dish dish = dishService.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                Setmeal setmeal = setmealService.getById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        // 只能查询自己的购物车
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 获取要删除的商品
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        shoppingCart= list.get(0);

        if(shoppingCart.getNumber() > 1){
            // 当前商品在购物车内有多个
            shoppingCart.setNumber(shoppingCart.getNumber() - 1);
            shoppingCartMapper.update(shoppingCart);
        }else{
            // 当前商品在购物车内只有一个
            shoppingCartMapper.deleteById(shoppingCart.getId());
        }
    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
        ShoppingCart build = ShoppingCart.builder().id(BaseContext.getCurrentId()).build();
        return shoppingCartMapper.list(build);
    }

    @Override
    public void clean() {
        shoppingCartMapper.deleteBatchByUserId(BaseContext.getCurrentId());
    }

}
