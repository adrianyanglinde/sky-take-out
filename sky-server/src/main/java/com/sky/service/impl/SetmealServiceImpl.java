package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    public SetmealMapper setmealMapper;

    @Autowired
    public SetmealDishMapper setmealDishMapper;

    @Autowired
    public DishMapper dishMapper;

    @Override
    public Setmeal getById(Long id) {
        return setmealMapper.getById(id);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    };


    @Override
    @Transactional
    public void saveWithDishes(SetmealDTO setmealDTO){

        // 向套餐表插入1条数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.insert(setmeal);

        // 向套餐菜品表插入数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }


    @Override
    @Transactional
    public void deleteBatch(List<Long> ids){
        // 启售的套餐不能删除
        for(Long id:ids){
            Setmeal setmeal = setmealMapper.getById(id);
            if(setmeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        for(Long id:ids){
            // 删除套餐表数据
            setmealMapper.deleteById(id);
            // 删除套餐菜品表数据
            setmealDishMapper.deleteBySetmealId(id);
        }
    }

    @Override
    public SetmealVO getByIdWithDishes(Long id){

        // 获取套餐详情
        Setmeal setmeal = setmealMapper.getById(id);
        // 获取套餐详情中的套餐和菜品的关联关系
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        // 构造setmealVO
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO){

        // 更新套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);

        // 更新套餐菜品表
        Long id = setmealDTO.getId();
        setmealDishMapper.deleteBySetmealId(id);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(id));
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public void setStatus(Long id,Integer status){
        // 启售套餐时，判断套餐内菜品是否有停售的
        if(status == StatusConstant.ENABLE){
            List<Dish> dishes = dishMapper.getBySetmealId(id);
            for(Dish dish:dishes){
                if(dish.getStatus() == StatusConstant.DISABLE){
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }

        // 更新套餐表中状态
        Setmeal setmeal = Setmeal.builder()
                                .id(id)
                                .status(status)
                                .build();
        setmealMapper.update(setmeal);
    }

    @Override
    public List<DishItemVO> getDishItemsById(Long id) {
        return setmealMapper.getDishItemsById(id);
    }


}
