package com.sky.service.impl;



import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.utils.HttpClientUtil;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrdersMapper orderMapper;

    @Override
    public BusinessDataVO businessData(LocalDateTime beginTime,LocalDateTime endTime) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        Integer newUsers = userMapper.countByMap(map);

        Integer orderCount = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;

        Integer validOrderCount = orderMapper.countByMap(map);

        Double orderCompletionRate = 0.0;
        if(orderCount != 0){
            orderCompletionRate = (double) validOrderCount / orderCount;
        }

        Double unitPrice = 0.0;
        if(validOrderCount != 0){
            unitPrice = turnover / validOrderCount;
        }

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO overviewOrders() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("beginTime",LocalDateTime.now().with(LocalTime.MIN));

        Integer allOrders = orderMapper.countByMap(map);

        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.countByMap(map);

        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders)
                .allOrders(allOrders)
                .build();
    }

    @Override
    public DishOverViewVO overviewDishes() {
        return null;
    }

    @Override
    public SetmealOverViewVO overviewSetmeals() {
        return null;
    }
}
