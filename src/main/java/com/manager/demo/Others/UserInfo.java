package com.manager.demo.Others;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MappedSuperclass
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public   int id;
    public String fullname;
    public String address;
    public String email;
    public String password;
    public String phonenumber;
    public String role;
}