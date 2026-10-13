package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.OrderSubmitVO;

public interface OrdersService {

    /**
     * 提交订单
     * @param ordersSubmitDTO
     */
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);


}
