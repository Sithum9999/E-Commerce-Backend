package org.icet.learn.controller.admin;

import lombok.RequiredArgsConstructor;
import org.icet.learn.entity.CouponEntity;
import org.icet.learn.exceptions.ValidationException;
import org.icet.learn.service.admin.coupon.AdminCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final AdminCouponService adminCouponService;

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponEntity coupon) {
        try {
            CouponEntity createdCoupon = adminCouponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CouponEntity>> getAllCoupons() {
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }

}

