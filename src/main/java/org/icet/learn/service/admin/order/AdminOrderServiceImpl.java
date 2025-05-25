package org.icet.learn.service.admin.order;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Order;
import org.icet.learn.entity.OrderEntity;
import org.icet.learn.enums.OrderStatus;
import org.icet.learn.repository.OrderDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderDao orderDao;

    public List<Order> getAllPlacedOrders() {
        List<OrderEntity> orderList = orderDao.findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));
        return orderList.stream().map(OrderEntity::getOrderDto).collect(Collectors.toList());
    }

}
