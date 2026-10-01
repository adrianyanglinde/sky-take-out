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
     * 插入数据
     * @param setmealDishes
     * @return
     */
    void insert(List<SetmealDish> setmealDishes);

}
