package com.manager.demo.Repos;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Long> {

    Feedback findByCustomerInfo(CustomerInfo customerInfo);
}
