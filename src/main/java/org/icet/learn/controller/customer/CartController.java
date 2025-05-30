package org.icet.learn.controller.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.icet.learn.dto.AddProductInCart;
import org.icet.learn.dto.Order;
import org.icet.learn.dto.PlaceOrder;
import org.icet.learn.exceptions.ValidationException;
import org.icet.learn.service.customer.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCart addProductInCart) {
        log.info("Cart: "+addProductInCart);
        return cartService.addProductToCart(addProductInCart);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId){
        Order orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        try {
            Order order = cartService.applyCoupon(userId, code);
            return ResponseEntity.ok(order);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<Order> increaseProductQuantity(@RequestBody AddProductInCart addProductInCart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCart));
    }

    @PostMapping("/deduction")
    public ResponseEntity<Order> decreaseProductQuantity(@RequestBody AddProductInCart addProductInCart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseProductQuantity(addProductInCart));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrder placeOrder) {
        log.info("Placed Order: "+placeOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrder));
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<Order>> getMyPlacedOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
    }

}