package com.manager.demo.Repos;

import com.manager.demo.Models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {

    @Query("SELECT DISTINCT LOWER(f.category) FROM FoodItem f")
    List<String> findDistinctCategoriesIgnoreCase();

    @Query("SELECT f FROM FoodItem f WHERE LOWER(f.name) LIKE %:searchQuery%")
    List<FoodItem> findByNameContainingIgnoreCase(String searchQuery);

    @Query("SELECT DISTINCT f.category FROM FoodItem f WHERE LOWER(f.name) LIKE %:searchQuery%")
    List<String> findCategoriesByItemNameContainingIgnoreCase(String searchQuery);

    FoodItem findByName(String name);

}
