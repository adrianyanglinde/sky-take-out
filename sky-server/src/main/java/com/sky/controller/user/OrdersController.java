package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import com.sky.service.UserService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单相关
 */
@RestController
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "订单相关接口")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    @ApiOperation("提交订单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("提交订单，参数:{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = ordersService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付，参数:{}",ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = ordersService.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }


}
