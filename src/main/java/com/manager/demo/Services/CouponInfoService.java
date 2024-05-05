package com.manager.demo.Services;

import com.manager.demo.Models.Coupon;
import com.manager.demo.Repos.CouponInfoRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class CouponInfoService {

    @Autowired
    private final CouponInfoRepo repo;

    public CouponInfoService(CouponInfoRepo repo) {
        this.repo = repo;
    }

    Coupon getCouponByID(int id)
    {
        return repo.findById(id);
    }

    List<Coupon> getCouponsByStatus(Boolean Status)
    {
        return repo.findByStatus(String.valueOf(Status));
    }

    void deleteById(int id)
    {
        repo.deleteById(id);
    }

    public void generateRandomCoupons(int numberOfCoupons, Date validDate) {
        List<Coupon> coupons = new ArrayList<>();

        for (int i = 0; i < numberOfCoupons; i++) {
            Coupon coupon = generateCoupon(validDate);

            coupons.add(coupon);
        }

        // Save all coupons to the database in a batch
        repo.saveAll(coupons);

    }

    private Coupon generateCoupon(Date validDate) {
        Coupon coupon = new Coupon();

        // Set valid date
        coupon.setValidDate(validDate);


        // Set status (assuming all coupons are initially active)
        coupon.setStatus("true");

        // Generate a random code
        String code = generateRandomCode();
        coupon.setCode(code);

        return coupon;
    }

    private String generateRandomCode() {
        // Define characters allowed in the code
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        int codeLength = 10; // Change the length of the code as needed

        // Generate random characters for the code
        for (int i = 0; i < codeLength; i++) {
            int index = (int) (Math.random() * characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }

    public Boolean isCouponUseable(String code) {
        Coupon coupon = repo.findCouponByCode(code);
        if(coupon == null)
            return false;

        if(coupon.getValidDate().before(new Date()))
            return false;

        return coupon.getStatus().equals("true");
    }

    public void useCoupon(String code) {
        Coupon coupon = repo.findCouponByCode(code);
        if(coupon == null) return;
        coupon.setStatus("false");
    }



}