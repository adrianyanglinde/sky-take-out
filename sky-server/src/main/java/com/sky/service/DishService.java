package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);


    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    void deleteBatch(List<Long> ids);

    /**
     * 获取菜品详情
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据分类id获取菜品
     * @param categoryId
     * @return
     */
    List<Dish> getByCategoryId(Long categoryId);


    /**
     * 更新菜品详情
     * @param dishDTO
     * @return
     */
     void updateWithFlavor(DishDTO dishDTO);


    /**
     * 菜品起售、停售
     * @param id
     * @param status
     * @return
     */
    void setStatus(Long id,Integer status);
}
