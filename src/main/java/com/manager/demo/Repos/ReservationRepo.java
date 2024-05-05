package com.manager.demo.Repos;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByCustomerInfo(CustomerInfo customerInfo);
}
