package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     * @param addressBook
     */
    void add(AddressBook addressBook);

    /**
     * 删除地址
     * @param id
     */
    void delete(Long id);

    /**
     * 修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 根据id获取地址
     * @param id
     */
    AddressBook getById(Long id);

    /**
     * 条件查询地址
     * @param addressbook
     * @return
     */
    List<AddressBook> list(AddressBook addressbook);

    /**
     * 设置登录用户默认地址
     * @param addressBook
     */
    void setDefault(AddressBook addressBook);


}
