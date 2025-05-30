package org.icet.learn.service.admin.order;

import org.icet.learn.dto.AnalyticsResponse;
import org.icet.learn.dto.Order;

import java.util.List;

public interface AdminOrderService {

    List<Order> getAllPlacedOrders();

    Order changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();

}
