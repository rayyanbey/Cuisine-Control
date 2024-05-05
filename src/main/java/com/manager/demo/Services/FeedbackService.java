package com.manager.demo.Services;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.Feedback;
import com.manager.demo.Repos.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private FeedbackRepo feedbackRepo;

    public FeedbackService(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    public Feedback findMyFeedback(CustomerInfo customerInfo) {
        return feedbackRepo.findByCustomerInfo(customerInfo);
    }

    public void saveFeedback(Feedback feedback) {
        feedbackRepo.save(feedback);
    }
}
