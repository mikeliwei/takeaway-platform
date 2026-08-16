package com.sky.service;

import com.sky.dto.OrdersDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderVO submit(OrdersDTO ordersDTO);

    PageResult historyOrders(Integer page, Integer pageSize, Integer status);

    OrderVO details(Long id);

    void userCancelById(Long id);
}
