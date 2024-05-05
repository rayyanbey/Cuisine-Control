package com.manager.demo.Controllers;


import com.manager.demo.Models.Coupon;
import com.manager.demo.Services.CouponInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CouponController {

    @Autowired
    private CouponInfoService service;

    @GetMapping("/generateCoupon")
    public String showCoupon(Model model)
    {
        model.addAttribute("coupon",new Coupon());
        return "generateCoupon";
    }

    @PostMapping("/generateCoupon")
    public String generateCoupons(@ModelAttribute("coupon") Coupon coupon, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/managerDashboard";
        }
        service.generateRandomCoupons(coupon.getAmount(), coupon.getValidDate());

        return "redirect:/generateCoupon";
    }


}
