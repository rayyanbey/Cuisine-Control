package com.manager.demo.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WaiterController {

    @GetMapping("/waiterdashboard")
    public String dashboard(HttpSession session) {
        if(session.getAttribute("waiter") == null)
            return "redirect:/login";
        return "Waiter/waiterDashboard";
    }
}

