package com.manager.demo.Repos;

import com.manager.demo.Models.WaiterInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WaiterInfoRepo extends JpaRepository<WaiterInfo,Long> {
    Optional<WaiterInfo> findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);

    Optional<WaiterInfo> findByFullname(String fullname);
}
