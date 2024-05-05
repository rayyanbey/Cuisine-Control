package com.manager.demo.Services;


import com.manager.demo.Models.WaiterInfo;
import com.manager.demo.Repos.WaiterInfoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaiterInfoService {


    private final WaiterInfoRepo repo;

    public WaiterInfoService(WaiterInfoRepo repo) {
        this.repo = repo;
    }

    public List<WaiterInfo> getAllWaiters()
    {
        return repo.findAll();
    }

    public WaiterInfo getWaiterByEmail(String email)
    {
        return repo.findByEmail(email).orElse(null);
    }

    public WaiterInfo saveNewWaiter(WaiterInfo waiterInfo)
    {
        return repo.save(waiterInfo);
    }
    public void deleteWaiter(int id)
    {
        repo.deleteById((long) id);
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        return repo.existsByEmailAndPassword(email, password);
    }

    public List<WaiterInfo> findAllWaiters()
    {
        return repo.findAll();
    }

    public void deleteWaiter(WaiterInfo waiterInfo) {
        repo.delete(waiterInfo);
    }
}
