package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);


    Page<Orders> page(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    @Select("select * from orders where status=#{pendingPayment} and order_time < #{localDateTime}")
    List<Orders> selectTimeout(Integer pendingPayment, LocalDateTime localDateTime);

    void updateBatch(List<Orders> orderList);
}
