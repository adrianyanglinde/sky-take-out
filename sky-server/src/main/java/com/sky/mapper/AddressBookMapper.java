package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 插入地址
     * @param addressBook
     */
    void insert(AddressBook addressBook);

    /**
     * 根据id删除地址
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * 更新地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 根据id获取地址
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * 条件查询地址
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 根据用户id更新当前用户的默认地址
     * @param addressBook
     */
    @Select("update address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateDefaultByUserId(AddressBook addressBook);
}
