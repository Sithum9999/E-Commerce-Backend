package org.icet.learn.controller;

import lombok.RequiredArgsConstructor;
import org.icet.learn.dto.Order;
import org.icet.learn.service.customer.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrackingController {

    private final CartService cartService;

    @GetMapping("/order/{trackingId}")
    public ResponseEntity<Order> searchOrderByTrackingId(@PathVariable UUID trackingId) {
        Order orderDto = cartService.searchOrderByTrackingId(trackingId);
        if (orderDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDto);
    }

}
