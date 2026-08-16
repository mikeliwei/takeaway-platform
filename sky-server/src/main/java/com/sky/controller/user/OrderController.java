package com.sky.controller.user;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/historyOrders")
    public Result<PageResult> historyOrders(Integer page, Integer pageSize, Integer status) {
        log.info("查询订单列表：{}, {}, {}", page, pageSize, status);
        PageResult res = orderService.historyOrders(page, pageSize, status);
        return Result.success(res);
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        orderService.userCancelById(id);
        return Result.success();
    }
}
