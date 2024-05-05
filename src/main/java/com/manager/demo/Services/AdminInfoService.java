package com.manager.demo.Services;

import com.manager.demo.Models.AdminInfo;
import com.manager.demo.Repos.AdminInfoRepo;
import org.springframework.stereotype.Service;

@Service
public class AdminInfoService {

    private final AdminInfoRepo repo;

    public AdminInfoService(AdminInfoRepo repo) {
        this.repo = repo;
    }

    public AdminInfo getAdminByEmail(String email)
    {
        return repo.findByEmail(email).orElse(null);
    }
    public boolean existsByEmailAndPassword(String email, String password) {
        return repo.existsByEmailAndPassword(email, password);
    }
}
