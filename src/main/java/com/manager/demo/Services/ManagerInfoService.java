package com.manager.demo.Services;

import com.manager.demo.Models.ManagerInfo;
import com.manager.demo.Repos.ManagerInfoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerInfoService  {

    private final ManagerInfoRepo repo;

    public ManagerInfoService(ManagerInfoRepo repo)
    {
        this.repo=repo;
    }
    public List<ManagerInfo> getAllManagers()
    {
        return repo.findAll();
    }

    public ManagerInfo getManagerByEmail(String email)
    {
        return repo.findByEmail(email).orElse(null);
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        return repo.existsByEmailAndPassword(email, password);
    }

    public void saveManager(ManagerInfo existingManager) {
        repo.save(existingManager);
    }

    public void deleteManager(ManagerInfo managerInfo) {
        repo.delete(managerInfo);
    }
}
