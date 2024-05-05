package com.manager.demo.Models;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private Long amount;
    @Email
    private String email;
    @NotBlank
    private String productName;
}
