package com.manager.demo.Controllers;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.Reservation;
import com.manager.demo.Services.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ReservationController {
    ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reserveTable")
    public String createReservationPage(HttpSession session, Model model) {
        CustomerInfo customer = (CustomerInfo) session.getAttribute("user");
        // Check if user is logged in
        if(customer == null) {
            return "redirect:/login";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String currentDate = now.format(formatter);

        // Create Reservation
        Reservation reservation = new Reservation();
        model.addAttribute("reservation", reservation);
        model.addAttribute("customer", customer);
        model.addAttribute("now", currentDate);
        return "Customer/reservation-page";
    }

    @PostMapping("/reserveTable")
    public String createReservation(HttpSession session ,@ModelAttribute("reservation") Reservation reservation) {

        CustomerInfo customer = (CustomerInfo) session.getAttribute("user");
        reservation.setCustomerInfo(customer);
        reservationService.createReservation(reservation);
        return "redirect:/myreservations";
    }

    @GetMapping("/myreservations")
    public String displayReservationPage(HttpSession session, Model model) {
        CustomerInfo customer = (CustomerInfo) session.getAttribute("user");
        if(customer == null) {
            return "redirect:/login";
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String currentDate = now.format(formatter);


        List<Reservation> reservations = reservationService.getMyReservations(customer.email);

        model.addAttribute("reservations", reservations);
        model.addAttribute("now", now);
        return "Customer/my-reservations";
    }

    @PostMapping("/deleteReservation/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/myreservations";
    }

}
