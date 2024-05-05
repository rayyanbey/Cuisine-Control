package com.manager.demo.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private int noStars;

    private String feedback;

    @OneToOne
    @JoinColumn(name = "customerinfo_id", nullable = false)
    private CustomerInfo customerInfo;
}
