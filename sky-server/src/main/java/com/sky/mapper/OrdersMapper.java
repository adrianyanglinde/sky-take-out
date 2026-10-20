package com.sky.mapper;

import com.sky.entity.Orders;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrdersMapper {

    /**
     * 插入订单
     * @param order
     */
    void insert(Orders order);

    /**
     * 更新订单
     * @param order
     */
    void update(Orders order);

    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

}
