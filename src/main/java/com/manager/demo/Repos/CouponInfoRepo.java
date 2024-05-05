package com.manager.demo.Repos;

import com.manager.demo.Models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface CouponInfoRepo extends JpaRepository<Coupon,Long> {
    // Find coupon by ID
    Coupon findById(int id);

    // Find coupons by valid date
    List<Coupon> findByValidDate(Date validDate);

    // Find coupons by status
    List<Coupon> findByStatus(String status);

    // Delete coupon by ID
    void deleteById(int id);

    Coupon findCouponByCode(String code);
}