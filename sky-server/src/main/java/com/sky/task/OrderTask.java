package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 处理支付超时订单
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder(){
        log.info("处理支付超时订单：{}",new Date());
        LocalDateTime limitPayDateTime = LocalDateTime.now().plusMinutes(-2);
        List<Orders> orderList = ordersMapper.getByStatusAndOrdertimeLT(Orders.PENDING_PAYMENT, limitPayDateTime);
        if(orderList != null && orderList.size() > 0){
            orderList.forEach(o -> {
                o.setStatus(Orders.CANCELLED);
                o.setCancelReason("支付超时，自动取消");
                o.setCancelTime(LocalDateTime.now());
                ordersMapper.update(o);
            });
        }
    }

    /**
     * 处理“派送中”状态的订单
     */
    @Scheduled(cron = "0 0 18 * * ?")
    public void processDeliverOrder(){
        log.info("处理“派送中”状态的订单：{}",new Date());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-30);
        List<Orders> orderList = ordersMapper.getByStatusAndOrdertimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if(orderList != null && orderList.size() > 0){
            orderList.forEach(o -> {
                o.setStatus(Orders.COMPLETED);
                ordersMapper.update(o);
            });
        }
    }


}
