package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SetmealMapper {

    /**
     * 插入套餐
     * @param setmeal
     */
    @AutoFill(operationType = OperationType.INSERT)
    void insert(Setmeal setmeal);


    /**
     * 分页查询
     * @param setmealPageQueryDTO
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id获取套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id删除套餐
     * @param id
     * @return
     */
    @Select("delete from setmeal where id = #{id}")
    void deleteById(Long id);

    /**
     * 更新套餐
     * @param setmeal
     * @return
     */
    @AutoFill(operationType = OperationType.UPDATE)
    void update(Setmeal setmeal);


    /**
     * 根据id获取套餐的菜品
     * @param id
     * @return
     */
    @Select("select d.name,sd.copies,d.image,d.description from setmeal_dish as sd left join dish as d on sd.dish_id = d.id\n" +
            "where sd.setmeal_id = #{id}")
    List<DishItemVO> getDishItemsById(Long id);
}
