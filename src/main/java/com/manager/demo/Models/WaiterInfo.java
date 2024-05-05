package com.manager.demo.Models;

import com.manager.demo.Others.UserInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "waiterinfo")
public class WaiterInfo extends UserInfo {

}
