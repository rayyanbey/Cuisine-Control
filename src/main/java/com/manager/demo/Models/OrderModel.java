package com.manager.demo.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity(name = "orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime placedOn;
    private Boolean isPaidOnline;
    private String address;
    private String city;
    private double price;

    @ManyToOne
    @JoinColumn(name = "customerinfo_id", nullable = true)
    CustomerInfo customerInfo;

    @ManyToOne
    @JoinColumn(name = "waiterinfo_id", nullable = true)
    WaiterInfo waiterInfo;
}
