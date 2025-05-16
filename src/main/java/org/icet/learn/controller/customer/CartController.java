package org.icet.learn.controller.customer;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.AddProductInCart;
import org.icet.learn.service.customer.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCart addProductInCart) {
        return cartService.addProductToCart(addProductInCart);
    }

}
