package com.sky.mapper;

import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品ids获取套餐ids
     * @param dishIds
     * @return
     */
//    @Select("select * from setmeal_dish where dish_id in (1,2,3,4)")
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 根据套餐ids获取菜品ids
     * @param setmealIds
     * @return
     */
    List<Long> getDishIdsBySetmealIds(List<Long> setmealIds);

    /**
     * 插入批量数据
     * @param setmealDishes
     * @return
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id删除数据
     * @param setmealId
     * @return
     */
    @Select("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据套餐id获取套餐和菜品关系
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);

}
