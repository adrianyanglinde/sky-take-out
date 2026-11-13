package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {


    /**
     * 数据概览
     * @return
     */
    BusinessDataVO businessData(LocalDateTime beginTime, LocalDateTime endTime);


    /**
     * 订单概览
     * @return
     */
    OrderOverViewVO overviewOrders();


    /**
     * 菜品总览
     * @return
     */
    DishOverViewVO overviewDishes();

    /**
     * 套餐总览
     * @return
     */
    SetmealOverViewVO overviewSetmeals();

}
