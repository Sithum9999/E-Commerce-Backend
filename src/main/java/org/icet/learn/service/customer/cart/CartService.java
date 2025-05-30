package org.icet.learn.service.customer.cart;

import org.icet.learn.dto.AddProductInCart;
import org.icet.learn.dto.Order;
import org.icet.learn.dto.PlaceOrder;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCart addProductInCart);

    Order getCartByUserId(Long userId);

    Order applyCoupon(Long userId, String code);

    Order increaseProductQuantity(AddProductInCart addProductInCart);

    Order decreaseProductQuantity(AddProductInCart addProductInCart);

    Order placeOrder(PlaceOrder placeOrder);

    List<Order> getMyPlacedOrders(Long userId);

    Order searchOrderByTrackingId(UUID trackingId);

}
