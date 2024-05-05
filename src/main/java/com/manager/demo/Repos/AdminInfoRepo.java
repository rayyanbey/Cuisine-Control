package com.manager.demo.Repos;

import com.manager.demo.Models.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdminInfoRepo extends JpaRepository<AdminInfo,Long> {

    Optional<AdminInfo> findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
}
