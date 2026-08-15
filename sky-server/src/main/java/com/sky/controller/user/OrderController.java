package com.sky.controller.user;

import com.sky.dto.OrdersDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public Result<OrderVO> submit(@RequestBody OrdersDTO ordersDTO) {
        log.info("提交订单：{}", ordersDTO);
        OrderVO orderVO = orderService.submit(ordersDTO);
        return Result.success(orderVO);
    }
}
