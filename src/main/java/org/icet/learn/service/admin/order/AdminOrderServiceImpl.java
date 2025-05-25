package org.icet.learn.service.admin.order;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Order;
import org.icet.learn.entity.OrderEntity;
import org.icet.learn.enums.OrderStatus;
import org.icet.learn.repository.OrderDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderDao orderDao;

    public List<Order> getAllPlacedOrders() {
        List<OrderEntity> orderList = orderDao.findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));
        return orderList.stream().map(OrderEntity::getOrderDto).collect(Collectors.toList());
    }

    public Order changeOrderStatus(Long orderId, String status) {
        Optional<OrderEntity> optionalOrder = orderDao.findById(orderId);
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();

            if (Objects.equals(status, "Shipped")) {
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }
            return orderDao.save(order).getOrderDto();
        }
        return null;
    }

}
