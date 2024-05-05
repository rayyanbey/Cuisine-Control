package com.manager.demo.Controllers;

import OrderManagement.Cart;
import OrderManagement.CartItem;
import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.FoodItem;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Repos.FoodItemRepo;
import com.manager.demo.Services.CouponInfoService;
import com.manager.demo.Services.FoodItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {
    private FoodItemService foodItemService;
    private CouponInfoService couponInfoService;
    @Autowired
    private HttpSession session;


    public MenuController(FoodItemService foodItemService, CouponInfoService couponInfoService)
    {
        this.foodItemService = foodItemService;
        this.couponInfoService = couponInfoService;
    }


    @GetMapping("/menu")
    public String getCustomerMenu(@RequestParam(name="search", required = false) String searchQuery, Model model) {

        List<FoodItem> items;
        List<String> categories;
        CustomerInfo c = (CustomerInfo) session.getAttribute("user");
        if(c == null) {
            return "redirect:/login";
        }

        if (searchQuery != null && !searchQuery.isEmpty()) {
            items = foodItemService.searchFoodItems(searchQuery);
            categories = foodItemService.getCategoryOfSearchedItems(searchQuery);
        } else {
            items = foodItemService.getAllFoodItems();
            categories = foodItemService.getAllCategories();
        }

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);

        return "Customer/menu";
    }

    @GetMapping("waiter/menu")
    public String getWaiterMenu(Model model) {
        List<FoodItem> items;
        List<String> categories;
        WaiterInfo w = (WaiterInfo) session.getAttribute("waiter");
        if(w == null) {
            return "redirect:/login";
        }

        items = foodItemService.getAllFoodItems();
        categories = foodItemService.getAllCategories();

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);

        return "Waiter/menu";
    }

    @PostMapping("/addToCart/{itemId}")
    public String addItemToCart(@PathVariable("itemId") Long itemId, Model model) {
        FoodItem item = foodItemService.getItemById(itemId).get();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            return "redirect:/login";
        }
        cart.addItem(item, 1);
        System.out.println(cart.getTotalPrice());
        if(session.getAttribute("waiter") == null)
            return getCustomerMenu(null, model);
        else
            return "redirect:/waiter/menu";
    }

    @GetMapping("/cart")
    public String displayCart(Model model) {
        if(session.getAttribute("cart") == null) {
            return  "redirect:/login";
        }
        Cart cart = (Cart) session.getAttribute("cart");
        String couponCode = cart.getCouponCode();
        model.addAttribute("cart", cart);
        model.addAttribute("code", couponCode);
        if(session.getAttribute("user") == null)
            return "Waiter/Cart";
        return "Customer/Cart";
    }

    @PostMapping("/increaseItem/{itemId}")
    public String increaseItemQunatity(@PathVariable("itemId") Long itemId) {
        FoodItem item = foodItemService.getItemById(itemId).get();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            return  "redirect:/login";
        }
        cart.addItem(item, 1);
        return "redirect:/cart";
    }

    @PostMapping("/decreaseItem/{itemId}")
    public String decreaseItemQunatity(@PathVariable("itemId") Long itemId) {
        FoodItem item = foodItemService.getItemById(itemId).get();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            return  "redirect:/login";
        }
        cart.removeItem(item);
        return "redirect:/cart";
    }

    @PostMapping("/applycoupon")
    public String applyCoupon(@RequestParam("code") String code, HttpSession session) {
        if(session.getAttribute("cart") == null) {
            return  "redirect:/login";
        }
       Boolean isCouponUsable = couponInfoService.isCouponUseable(code);
       if(isCouponUsable) {
           System.out.println("useable coupon code: " + code);
           Cart cart = (Cart) session.getAttribute("cart");
           cart.applyCoupon(code);
       }
        else {
           System.out.println("not useable coupon code : " + code);
       }
        return "redirect:/cart";
    }

}
