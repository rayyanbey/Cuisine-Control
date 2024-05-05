package com.manager.demo.Services;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Repos.CustomerInfoRepo;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoService {

    private final CustomerInfoRepo repo;

    public CustomerInfoService(CustomerInfoRepo repo) {
        this.repo = repo;
    }

    public CustomerInfo getCustomerByEmail(String email)
    {
        return repo.findByEmail(email).orElse(null);
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        return repo.existsByEmailAndPassword(email, password);
    }

    public Boolean isCustomerPresent(String email) {
        return repo.findByEmail(email).isPresent();
    }

    public void saveCustomer(CustomerInfo customerInfo) {
        repo.save(customerInfo);
    }
}
