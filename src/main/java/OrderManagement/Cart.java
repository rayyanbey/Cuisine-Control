package OrderManagement;

import com.manager.demo.Models.FoodItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Getter
@Setter
public class Cart {
    private List<CartItem> items;
    private double totalPrice;
    private Boolean isCouponApplied;
    private String couponCode;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        couponCode = "";
        isCouponApplied = false;
    }

    public void addItem(FoodItem foodItem, Integer quantity) {
        if(quantity == null) {
            quantity = 1;
        }
        for (CartItem item : items) {
            if (item.getFoodItem().equals(foodItem)) {
                item.setQuantity(item.getQuantity() + quantity);
                totalPrice += foodItem.getPrice();
                return;
            }
        }
        // If item is not in the cart, create a new CartItem and add it to the cart
        CartItem newItem = new CartItem(foodItem, quantity);
        items.add(newItem);
        totalPrice += foodItem.getPrice();
    }

    public void removeItem(FoodItem foodItem) {
        for (Iterator<CartItem> iterator = items.iterator(); iterator.hasNext();) {
            CartItem item = iterator.next();
            if (item.getFoodItem().equals(foodItem)) {
                if (item.getQuantity() > 1) {
                    // If quantity is more than 1, decrease quantity and update total price
                    item.setQuantity(item.getQuantity() - 1);
                    totalPrice -= foodItem.getPrice();
                } else {
                    // If quantity is 1, remove the item from the cart
                    iterator.remove();
                    totalPrice -= foodItem.getPrice();
                }
                return;
            }
        }
    }

    public void clearCart() {
        items = new ArrayList<>();
        totalPrice = 0;
        isCouponApplied = false;
        couponCode = "";
    }

    public void applyCoupon(String code) {
        this.couponCode = code;
        isCouponApplied = true;
    }

    public void removeCoupon() {
        isCouponApplied = false;
        this.couponCode = "";
    }

    public double getFinalPrice() {
        double price = totalPrice;
        if(isCouponApplied) {
            price -= .05 * price;
        }
        return price;
    }

    public double getDiscountPrice() {
        return isCouponApplied ? 0.05 * totalPrice : 0;
    }
}
