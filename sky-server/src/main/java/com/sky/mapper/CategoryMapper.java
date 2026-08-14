package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    /**
     * 插入分类
     * @param category
     */
    @Select("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{type}, #{name},#{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Select("delete from category where id = #{id}")
    void delete(Long id);

    /**
     * 更新分类
     * @param category
     */
    void update(Category category);


    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
