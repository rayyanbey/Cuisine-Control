package com.manager.demo.Models;


import com.manager.demo.Others.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customerinfo")
public class CustomerInfo extends UserInfo {


    //nothing to add
}
