package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder() {
        log.info("定时处理超时订单");
        List<Orders> orderList = orderMapper.selectTimeout(Orders.PENDING_PAYMENT, LocalDateTime.now().minusMinutes(15));
        if (orderList != null && !orderList.isEmpty()) {
            orderList.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelTime(LocalDateTime.now());
                order.setCancelReason("超时未付款");
            });
            orderMapper.updateBatch(orderList);
        }
    }
}
