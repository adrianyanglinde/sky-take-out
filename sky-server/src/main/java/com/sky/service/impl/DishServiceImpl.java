package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.DishFlavor;
import com.sky.entity.Employee;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sky.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    public DishMapper dishMapper;
    @Autowired
    public DishFlavorMapper dishFlavorMapper;
    @Autowired
    public SetmealDishMapper setmealDishMapper;

    @Override
    public List<Dish> list(Dish dish){
        // 查询菜品数据
        return dishMapper.list(dish);
    }

    @Override
    public DishVO getByIdWithFlavor(Long id){
        // 查询菜品数据
        Dish dish = dishMapper.getById(id);
        // 查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.getFlavorByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }


    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishes = list(dish);
        List<DishVO> dishVOS = new ArrayList<>();
        for(Dish d: dishes){
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);
            List<DishFlavor> dishFlavors = dishFlavorMapper.getFlavorByDishId(d.getId());
            dishVO.setFlavors(dishFlavors);
            dishVOS.add(dishVO);
        }
        return dishVOS;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO){

        // 更新菜品数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);

        // 更新口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            dishFlavorMapper.deleteByDishId(dishDTO.getId());
            flavors.forEach(flavor -> {
                flavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO){

        //向菜品表插入1条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);

        //向口味表插入n条数据
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO){
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> records = page.getResult();
        return new PageResult(total,records);
    };

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids){
        // 启售的菜品不能删除
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 关联了套餐的菜品不能被删除
        List<Long> setMealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setMealIds.size() > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 删除表中的菜品数据
        for(Long id: ids){
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    @Override
    public void setStatus(Long id,Integer status){
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);

        //TODO： 如果停售，当前菜品的套餐也要停售

    }



}
