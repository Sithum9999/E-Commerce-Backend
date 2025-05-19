package org.icet.learn.service.admin.coupon;

import lombok.RequiredArgsConstructor;
import org.icet.learn.entity.CouponEntity;
import org.icet.learn.exceptions.ValidationException;
import org.icet.learn.repository.CouponDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponDao couponDao;

    public CouponEntity createCoupon(CouponEntity coupon) {
        if (couponDao.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon code already exists.");
        }
        return couponDao.save(coupon);
    }

    public List<CouponEntity> getAllCoupons() {
        return couponDao.findAll();
    }

}
