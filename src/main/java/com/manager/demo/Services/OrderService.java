package com.manager.demo.Services;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.OrderModel;
import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Repos.OrderRepo;
import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void createOrder(OrderModel order) {
        orderRepo.save(order);
        System.out.println("orderere: " + order.getId());
    }

    public OrderModel getOrderById(Long id) {
        return orderRepo.getOrderModelById(id);
    }
    public void payOrder(OrderModel order) {
        order.setIsPaidOnline(true);
        orderRepo.save(order);
    }

    public List<OrderModel> getWaiterOrders(WaiterInfo waiterInfo) {
        return orderRepo.findAllByWaiterInfo(waiterInfo);
    }

    public List<OrderModel> getWaiterPendingOrders(WaiterInfo waiterInfo) {
        return  orderRepo.findByWaiterInfoAndStatus(waiterInfo, "processing");
    }
    public List<OrderModel> getWaiterCompletedOrders(WaiterInfo waiterInfo) {
        return orderRepo.findByWaiterInfoAndStatus(waiterInfo, "completed");
    }
    public List<OrderModel> getMyOrders(CustomerInfo customer) {
        return orderRepo.findAllByCustomerInfo(customer);
    }

    public void deleteOrderById(Long orderId) {
        orderRepo.deleteById(orderId);
    }

    public void completeOrderById(Long orderId) {
        OrderModel orderModel = orderRepo.findById(orderId).get();
        orderModel.setStatus("completed");
        orderRepo.save(orderModel);
    }
}
