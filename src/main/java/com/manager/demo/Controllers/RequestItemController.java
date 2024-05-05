package com.manager.demo.Controllers;


import com.manager.demo.Models.FoodItem;
import com.manager.demo.Repos.FoodItemRepo;
import com.manager.demo.Services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.model.IModel;

@Controller
public class RequestItemController {

    private final FoodItemService service;


    @Autowired
    private FoodItemRepo repo;

    public RequestItemController(FoodItemService service) {
        this.service = service;
    }

    @GetMapping("/requestItem")
    public String showRequestForm(Model model)
    {
        model.addAttribute("fooditem", new FoodItem());
        return  "requestItem";
    }

    @PostMapping("/requestItem")
    public String saveItems(@ModelAttribute FoodItem fooditem, BindingResult result,Model model)
    {
        if(result.hasErrors())
        {
            model.addAttribute("error","CANNOT PROCEED");
            return "redirect:/requestItem";
        }
        fooditem.setStatus(false);
        repo.save(fooditem);
        return "redirect:/managerDashboard/temp";
    }


}
