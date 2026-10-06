package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void saveWithDishes(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id获取套餐
     * @param id
     */
    Setmeal getById(Long id);

    /**
     * 根据id获取套餐和菜品
     * @param id
     */
    SetmealVO getByIdWithDishes(Long id);

    /**
     * 条件查询套餐
     * @param
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     * @param id
     * @param status
     */
    void setStatus(Long id,Integer status);

    /**
     * 根据id获取套餐的菜品
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemsById(Long id);
}
