package com.manager.demo.Controllers;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Services.CustomerInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class SignUpController {

    CustomerInfoService customerInfoService;

    public SignUpController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        CustomerInfo customerInfo = new CustomerInfo();
        model.addAttribute("customer", customerInfo);
        return "signup";
    }

    @PostMapping("/signup")
    public String createAccount(@ModelAttribute CustomerInfo customerInfo,
                                @RequestParam("cpassword") String pass
                                ) {

        if(customerInfoService.isCustomerPresent(customerInfo.email))
            return "redirect:/signup";
        if(!Objects.equals(customerInfo.password, pass))
            return "redirect:/signup";
        customerInfo.setRole("customer");
        customerInfoService.saveCustomer(customerInfo);
        return "redirect:/login";
    }

}
