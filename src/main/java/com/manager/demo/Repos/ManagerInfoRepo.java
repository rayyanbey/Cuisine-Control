package com.manager.demo.Repos;

import com.manager.demo.Models.ManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ManagerInfoRepo extends JpaRepository<ManagerInfo,Long> {

    Optional<ManagerInfo> findByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);

    Optional<Object> findByFullname(String fullname);
}
