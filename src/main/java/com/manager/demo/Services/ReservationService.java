package com.manager.demo.Services;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.Reservation;
import com.manager.demo.Repos.CustomerInfoRepo;
import com.manager.demo.Repos.ReservationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    ReservationRepo reservationRepo;
    CustomerInfoRepo customerInfoRepo;
    public ReservationService(ReservationRepo reservationRepo, CustomerInfoRepo customerInfoRepo) {
        this.reservationRepo = reservationRepo;
        this.customerInfoRepo = customerInfoRepo;
    }

    public void createReservation(Reservation reservation) {
        reservationRepo.save(reservation);
    }

    public List<Reservation> getMyReservations(String email) {
        CustomerInfo customer =  customerInfoRepo.findByEmail(email).get();
        return reservationRepo.findAllByCustomerInfo(customer);
    }

    public void deleteReservation(Long id) {
        reservationRepo.deleteById(id);
    }
}
