package com.manager.demo.Repos;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.OrderModel;
import com.manager.demo.Models.WaiterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel, Long> {
    OrderModel getOrderModelById(Long id);

    List<OrderModel> findAllByCustomerInfo(CustomerInfo customerInfo);

    List<OrderModel> findAllByWaiterInfo(WaiterInfo waiterInfo);

    List<OrderModel> findByWaiterInfoAndStatus(WaiterInfo waiterInfo, String status);

}
