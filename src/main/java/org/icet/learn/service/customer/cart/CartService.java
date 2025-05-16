package org.icet.learn.service.customer.cart;

import org.icet.learn.dto.AddProductInCart;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCart addProductInCart);
}
