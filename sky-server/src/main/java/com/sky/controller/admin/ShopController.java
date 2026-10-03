package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 店铺管理
 */
@RestController
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺操作接口")
public class ShopController {

    public static final String key = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result<String> setStatus(@PathVariable Integer status){
        log.info("设置营业状态，参数：{}",status);
        redisTemplate.opsForValue().set(key,status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result<Integer> getStatus(){
        log.info("获取营业状态");
        Integer status = (Integer) redisTemplate.opsForValue().get(key);
        return Result.success(status);
    }


}
