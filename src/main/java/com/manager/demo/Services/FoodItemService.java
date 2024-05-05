package com.manager.demo.Services;

import com.manager.demo.Models.FoodItem;
import com.manager.demo.Repos.FoodItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    private FoodItemRepo foodItemRepo;

    public FoodItemService(FoodItemRepo foodItemRepo) {
        this.foodItemRepo = foodItemRepo;
    }

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepo.findAll();
    }
    public List<String> getAllCategories() {
        return foodItemRepo.findDistinctCategoriesIgnoreCase();
    }

    public List<FoodItem> searchFoodItems(String searchQuery) {
        return foodItemRepo.findByNameContainingIgnoreCase(searchQuery.toLowerCase());
    }

    public List<String> getCategoryOfSearchedItems(String searchQuery) {
        return foodItemRepo.findCategoriesByItemNameContainingIgnoreCase(searchQuery.toLowerCase());
    }

    public Optional<FoodItem> getItemById(Long id) {
        return foodItemRepo.findById(id);
    }

    public FoodItem getItemByName(String name) {

        return foodItemRepo.findByName(name);
    }

    public void deleteItem(FoodItem foodItem) {

        foodItemRepo.delete(foodItem);
    }
}
