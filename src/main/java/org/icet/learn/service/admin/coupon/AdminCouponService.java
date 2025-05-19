package org.icet.learn.service.admin.coupon;

import org.icet.learn.entity.CouponEntity;

import java.util.List;

public interface AdminCouponService {
    CouponEntity createCoupon(CouponEntity coupon);
    List<CouponEntity> getAllCoupons();
}
