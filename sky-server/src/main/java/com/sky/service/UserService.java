package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.Employee;
import com.sky.entity.User;
import com.sky.result.PageResult;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     */
    User wxLogin(UserLoginDTO userLoginDTO);


    /**
     * 调用微信登录接口服务，获得当前微信用户的openid
     * @param code
     */
    String getOpenid(String code);

}
