package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface CategoryService {

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param id
     */
    void delete(Long id);

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    Category update(CategoryDTO categoryDTO);

    /**
     * 修改分类状态
     * @param status
     * @param id
     */
    void changeStatus(Integer status,Long id);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);


}
