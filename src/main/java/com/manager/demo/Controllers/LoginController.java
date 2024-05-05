package com.manager.demo.Controllers;

import OrderManagement.Cart;
import com.manager.demo.Models.AdminInfo;
import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.ManagerInfo;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Services.AdminInfoService;
import com.manager.demo.Services.CustomerInfoService;
import com.manager.demo.Services.ManagerInfoService;
import com.manager.demo.Services.WaiterInfoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final ManagerInfoService managerInfoService;
    private final WaiterInfoService waiterInfoService;
    private final AdminInfoService adminInfoService;
    private final CustomerInfoService customerInfoService;

    @Autowired
    public LoginController(ManagerInfoService managerInfoService, WaiterInfoService waiterInfoService,
                           AdminInfoService adminInfoService, CustomerInfoService customerInfoService) {
        this.managerInfoService = managerInfoService;
        this.waiterInfoService = waiterInfoService;
        this.adminInfoService = adminInfoService;
        this.customerInfoService = customerInfoService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /*@GetMapping("/adminDashboard")
    public String showAdmin() {
        return "adminDashboard";
    }
    @GetMapping("/customerDashboard")
    public String showCustomer() {
        return "customerDashboard";
    }
    @GetMapping("/waiterDashboard")
    public String showWaiter() {
        return "waiterDashboard";
    }*/
   /* @GetMapping("/managerDashboard")
    public String showmanager() {
        return "managerDashboard";
    }*/

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        // Determine user role based on the email
        String role = determineUserRole(email,password, session);

        if (role == null) {
            // No user found with the provided email
            model.addAttribute("error", "Invalid email or password.");
            return "login"; // Return to login page with error message
        }


        // Redirect to the respective dashboard based on the user role
        switch (role) {
            case "Manager":
                return "redirect:/managerDashboard";
            case "Admin":
                return "redirect:/adminDashboard";
            case "Waiter":
                return "redirect:/waiterdashboard";
            case "Customer":
                return "redirect:/home";
            default:
                // This should not happen, but handle it just in case
                model.addAttribute("error", "Invalid email or password.");
                return "login";
        }
    }

    @PostMapping("/logout")
    String logout(HttpSession session) {
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    private String determineUserRole(String email,String password, HttpSession session) {
        ManagerInfo managerInfo = managerInfoService.getManagerByEmail(email);
        if (managerInfo != null && managerInfo.getPassword().equals(password)) {
            session.setAttribute("manager", managerInfo);
            return "Manager";
        }

        AdminInfo adminInfo = adminInfoService.getAdminByEmail(email);
        if (adminInfo != null && adminInfo.getPassword().equals(password)) {
            session.setAttribute("admin", adminInfo);
            return "Admin";
        }

        CustomerInfo customerInfo = customerInfoService.getCustomerByEmail(email);
        if (customerInfo != null && customerInfo.getPassword().equals(password)) {
            session.setAttribute("user", customerInfo);
            session.setAttribute("cart", new Cart());
            return "Customer";
        }

        WaiterInfo waiterInfo = waiterInfoService.getWaiterByEmail(email);
        if (waiterInfo != null && waiterInfo.getPassword().equals(password)) {
            session.setAttribute("cart", new Cart());
            session.setAttribute("waiter", waiterInfo);
            return "Waiter";
        }

        return null; // No user found with the provided email
    }


}


