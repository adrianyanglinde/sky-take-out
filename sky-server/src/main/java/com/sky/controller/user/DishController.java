package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    public DishService dishService;

    @Autowired
    public RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> getByCategoryId(@RequestParam Long categoryId){
        log.info("根据分类id查询菜品，参数为：{}", categoryId);

        // 构造redis中的key，规则：dishes_分类id
        String key = "dishes_" + categoryId;

        // 查询redis
        List<DishVO> dishes = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishes != null && dishes.size() > 0){
            return Result.success(dishes);
        }

        // 查询数据库
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dishes = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key,dishes);
        return Result.success(dishes);
    }

}
