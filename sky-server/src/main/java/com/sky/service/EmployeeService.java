package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 编辑员工
     * @param employeeDTO
     * @return
     */
    Employee update(EmployeeDTO employeeDTO);

    /**
     * 修改员工状态
     * @param status
     * @param id
     */
    void changeStatus(Integer status,Long id);

    /**
     * 获取员工数据
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
