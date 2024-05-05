package com.manager.demo.Controllers;

import OrderManagement.Cart;
import OrderManagement.CartItem;
import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.OrderModel;
import com.manager.demo.Models.Request;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Services.CouponInfoService;
import com.manager.demo.Services.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class CheckoutController {

    @Autowired
    OrderService orderService;
    @Autowired
    CouponInfoService couponInfoService;

    @Value("${stripe.api.publickey}")
    private String publicKey;

    @Autowired
    HttpSession session;

    @GetMapping("/payforOrder/{orderId}")
    public String showCard(@PathVariable("orderId") Long orderId, Model model) {

        CustomerInfo customerInfo = (CustomerInfo) session.getAttribute("user");

        OrderModel order = orderService.getOrderById(orderId);
        if(customerInfo == null) return "redirect:/login";

        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", order.getPrice());
        model.addAttribute("email", customerInfo.getEmail());
        model.addAttribute("productName", orderId.toString());

        return "checkout";
    }

    @GetMapping("/checkoutpage")
    public String getCheckoutPage(HttpSession session, Model model) {
        CustomerInfo customerInfo = (CustomerInfo) session.getAttribute("user");
        if(customerInfo == null) {
            return "redirect:/login";
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart.getItems().isEmpty())
            return "redirect:/menu";

        model.addAttribute("customer", customerInfo);
        return "Customer/checkout";
    }

    @PostMapping("/placeorder")
    public String placeOrder(@RequestParam("address") String address,
                             @RequestParam("city") String city,
                             @RequestParam("paymentMethod") String paymentMethod
    ) {
        System.out.println(paymentMethod);
        System.out.println(address);
        System.out.println(city);

        OrderModel order = createOrder(address, city);
        if(paymentMethod.equals("online")) {
            return "redirect:/payforOrder/" + order.getId();
        }

        return "redirect:/home";
    }

    @PostMapping("/createwaiterorder")
    public String createWaiterOrder() {
        OrderModel order = new OrderModel();
        Cart cart = (Cart) session.getAttribute("cart");

        WaiterInfo waiterInfo = (WaiterInfo) session.getAttribute("waiter");

        if(waiterInfo == null)
            return "redirect:/login";
        order.setWaiterInfo(waiterInfo);
        order.setIsPaidOnline(false);
        order.setPrice(cart.getFinalPrice());
        order.setStatus("processing");
        order.setPlacedOn(LocalDateTime.now());

        couponInfoService.useCoupon(cart.getCouponCode());
        orderService.createOrder(order);
        cart.clearCart();
        return "redirect:/waiterdashboard";
    }

    @GetMapping("/confirmpayment/{orderId}/{intentId}")
    public String paymentConfirmPage(@PathVariable("intentId") String id, @PathVariable("orderId") String orderId) throws StripeException {

        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        String status = paymentIntent.getStatus();

        if ("succeeded".equals(status)) {
            System.out.println("Payment successful!");
            OrderModel order = orderService.getOrderById(Long.parseLong(orderId));
            orderService.payOrder(order);
        } else if ("requires_action".equals(status)) {
            System.out.println("Payment requires further action.");
        } else if ("requires_payment_method".equals(status)) {
            System.out.println("Payment requires a valid payment method.");
        } else {
            System.out.println("Payment failed or status unknown.");
        }

        return "redirect:/home";
    }


    private OrderModel createOrder(String address, String city) {
        OrderModel order = new OrderModel();
        CustomerInfo customerInfo = (CustomerInfo) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        order.setAddress(address);
        order.setCity(city);
        order.setCustomerInfo(customerInfo);
        order.setIsPaidOnline(false);
        order.setPrice(cart.getFinalPrice());
        order.setStatus("processing");
        order.setPlacedOn(LocalDateTime.now());

        couponInfoService.useCoupon(cart.getCouponCode());
        orderService.createOrder(order);
        cart.clearCart();
        return order;
    }
}
