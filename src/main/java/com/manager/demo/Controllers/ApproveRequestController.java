package com.manager.demo.Controllers;


import com.manager.demo.Models.FoodItem;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Repos.FoodItemRepo;
import com.manager.demo.Services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApproveRequestController {

    private final FoodItemService foodItemService;

    @Autowired
    private FoodItemRepo repo;

    public ApproveRequestController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping("/ApproveRequest")
    public String showRequests(Model model) {
        List<FoodItem> temp = foodItemService.getAllFoodItems();
        List<FoodItem> foodItems = new ArrayList<>(); // Initialize foodItems list
        for (int i = 0; i < temp.size(); i++) {
            if (!temp.get(i).getStatus()) {
                foodItems.add(temp.get(i));
            }
        }
        model.addAttribute("fooditems", foodItems);
        return "ApproveRequest";
    }

    @GetMapping ("/ApproveRequest/{name}/delete")
    public String deleteItem(@PathVariable String name, Model model) {
        // Call the service method to delete the waiter by email
        FoodItem foodItem = foodItemService.getItemByName(name);
        foodItemService.deleteItem(foodItem);
        return "redirect:/ApproveRequest";
    }

    @GetMapping ("/ApproveRequest/{name}/edit")
    public String approve(@PathVariable String name, Model model) {
        // Call the service method to delete the waiter by email
        FoodItem foodItem = foodItemService.getItemByName(name);
        foodItem.setStatus(true);

        repo.save(foodItem);
        return "redirect:/ApproveRequest";
    }


}
