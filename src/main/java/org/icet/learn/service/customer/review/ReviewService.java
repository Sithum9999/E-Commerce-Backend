package org.icet.learn.service.customer.review;

import org.icet.learn.dto.OrderedProductsResponse;

public interface ReviewService {

    OrderedProductsResponse getOrderedProductsDetailsByOrderId(Long orderId);

}
