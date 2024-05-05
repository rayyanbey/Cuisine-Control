package com.manager.demo.Controllers;


import com.manager.demo.Models.FoodItem;
import com.manager.demo.Services.FoodItemService;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class showITEMS {

    private final FoodItemService service;

    public showITEMS(FoodItemService service) {
        this.service = service;
    }


    @GetMapping("/managerDashboard/temp")
    public String showAllItems(Model model) {
        List<FoodItem> foodItems = service.getAllFoodItems();
        List<FoodItem> newList = new ArrayList<>(); // Initialize the newList to avoid NullPointerException
        for (FoodItem foodItem : foodItems) {
            if (!foodItem.getStatus()) {
                newList.add(foodItem);
            }
        }
        model.addAttribute("fooditems", newList); // Add the filtered list to the model instead of the original list
        return "temp";
    }
}
