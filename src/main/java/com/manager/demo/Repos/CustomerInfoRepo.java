package com.manager.demo.Repos;

import com.manager.demo.Models.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CustomerInfoRepo extends JpaRepository<CustomerInfo,Long> {
    Optional<CustomerInfo> findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
}
