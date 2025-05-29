package org.icet.learn.service.customer.review;

import org.icet.learn.dto.OrderedProductsResponse;
import org.icet.learn.dto.Review;

import java.io.IOException;

public interface ReviewService {

    OrderedProductsResponse getOrderedProductsDetailsByOrderId(Long orderId);
    Review giveReview(Review review) throws IOException;

}
