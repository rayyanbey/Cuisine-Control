package com.manager.demo.Controllers;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.OrderModel;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Services.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    HttpSession session;

    @Autowired
    OrderService orderService;

    @GetMapping("/myorders")
    public String getMyOrders(Model model) {
        CustomerInfo customerInfo = (CustomerInfo) session.getAttribute("user");
        if(customerInfo == null) {
            return "redirect:/login";
        }

        List<OrderModel> orders = orderService.getMyOrders(customerInfo);
        model.addAttribute("orders", orders);
        return "Customer/my-orders";
    }

    @GetMapping("/waiter/myorders/pending")
    public String getWaiterPendingOrders(Model model) {
        WaiterInfo waiterInfo = (WaiterInfo) session.getAttribute("waiter");
        if(waiterInfo == null)
            return "redirect:/login";

        List<OrderModel> orders = orderService.getWaiterPendingOrders(waiterInfo);
        model.addAttribute("orders", orders);
        return "Waiter/pendingOrders";
    }

    @PostMapping("/waiter/deleteorder/{orderId}")
    public String deleteWaiterOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrderById(orderId);
        return "redirect:/waiter/myorders/pending";
    }

    @PostMapping("/waiter/completeorder/{orderId}")
    public String completeWaiterOrder(@PathVariable("orderId") Long orderId) {
        orderService.completeOrderById(orderId);
        return "redirect:/waiter/myorders/pending";
    }

    @GetMapping("/waiter/myorders")
    public String getWaiterOrders(Model model) {
        WaiterInfo waiterInfo = (WaiterInfo) session.getAttribute("waiter");
        if(waiterInfo == null)
            return "redirect:/login";

        List<OrderModel> orders = orderService.getWaiterOrders(waiterInfo);
        model.addAttribute("orders", orders);
        return "Waiter/totalOrders";
    }

    @GetMapping("/waiter/myorders/completed")
    public String getWaiterCompletedOrders(Model model) {
        WaiterInfo waiterInfo = (WaiterInfo) session.getAttribute("waiter");
        if(waiterInfo == null)
            return "redirect:/login";

        List<OrderModel> orders = orderService.getWaiterCompletedOrders(waiterInfo);
        model.addAttribute("orders", orders);
        return "Waiter/totalOrders";
    }
}
