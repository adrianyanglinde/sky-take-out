package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址相关接口")
public class AddressBookController {

    @Autowired
    public AddressBookService addressBookService;

    @PostMapping
    public Result<String> add(@RequestBody AddressBook addressBook){
        log.info("新增地址，参数：{}",addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    @DeleteMapping()
    public Result<String> delete(@RequestParam Long id){
        log.info("删除地址，参数：{}",id);
        addressBookService.delete(id);
        return Result.success();
    }

    @PutMapping()
    public Result<String> update(@RequestBody AddressBook addressBook){
        log.info("修改地址，参数：{}",addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id){
        log.info("根据id获取地址，参数：{}",id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        log.info("查询登录用户地址列表");
        AddressBook addressbook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        List<AddressBook> list = addressBookService.list(addressbook);
        return Result.success(list);
    }

    @GetMapping("/default")
    public Result<AddressBook> getDefault(){
        log.info("查询登录用户默认地址");
        AddressBook addressbook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .isDefault(1)
                .build();
        List<AddressBook> list = addressBookService.list(addressbook);
        if(list != null && list.size() > 0) {
            Result.success(list.get(0));
        }
        return Result.error("没有默认地址");
    }

    @PutMapping("/default")
    public Result<String> setDefault(@RequestBody AddressBook addressBook){
        log.info("设置默认地址，参数:{}",addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }
}
