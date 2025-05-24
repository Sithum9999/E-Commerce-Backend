package org.icet.learn.service.customer.cart;

import org.icet.learn.dto.AddProductInCart;
import org.icet.learn.dto.Order;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCart addProductInCart);

    Order getCartByUserId(Long userId);

    Order applyCoupon(Long userId, String code);

    Order increaseProductQuantity(AddProductInCart addProductInCart);

    Order decreaseProductQuantity(AddProductInCart addProductInCart);

}
