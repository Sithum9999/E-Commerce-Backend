package org.icet.learn.service.admin.order;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.AnalyticsResponse;
import org.icet.learn.dto.Order;
import org.icet.learn.entity.OrderEntity;
import org.icet.learn.enums.OrderStatus;
import org.icet.learn.repository.OrderDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    public AnalyticsResponse calculateAnalytics() {
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1); // Assuming monthsToSubtract is 1 for "previousMonth"

        Long currentMonthOrders = getTotalOrdersForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long currentMonthEarnings = getTotalEarningsForMonth(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarnings = getTotalEarningsForMonth(previousMonthDate.getMonthValue(), previousMonthDate.getYear());

        Long placed = orderDao.countByOrderStatus(OrderStatus.Placed);
        Long shipped = orderDao.countByOrderStatus(OrderStatus.Shipped);
        Long delivered = orderDao.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponse(placed, shipped, delivered, currentMonthOrders, previousMonthOrders,
                currentMonthEarnings, previousMonthEarnings);
    }

    public long getTotalOrdersForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        // Move the calendar to the end of the specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<OrderEntity> orders = orderDao.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        return (long) orders.size();
    }

    public Long getTotalEarningsForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        // Move the calendar to the end of the specified month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<OrderEntity> orders = orderDao.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        Long sum = 0L;
        for (OrderEntity order : orders) {
            sum += order.getAmount();
        }

        return sum;
    }

}
