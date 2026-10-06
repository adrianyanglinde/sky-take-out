package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 商品插入购物车
     * @param shoppingCart
     */
    void insert(ShoppingCart shoppingCart);

    /**
     * 条件查询购物车
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车商品
     * @param shoppingCart
     * @return
     */
    void update(ShoppingCart shoppingCart);

    /**
     * 根据id获取购物车商品
     * @param id
     * @return
     */
    @Delete("select from shopping_cart where id = #{id}")
    void getById(Long id);

    /**
     * 根据id删除购物车商品
     * @param id
     * @return
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    /**
     * 批量删除购物车商品
     * @param userId
     * @return
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteBatchByUserId(Long userId);

}
