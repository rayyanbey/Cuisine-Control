package com.manager.demo.Controllers;

import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.Feedback;
import com.manager.demo.Services.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerInfoController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/home")
    public String homepage(HttpSession session, Model model) {
        if(session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

        model.addAttribute("feedbacks", feedbacks);
        return "Customer/homepage";
    }

    @GetMapping("/feedback")
    public String feedbackPage(HttpSession session, Model model) {
        CustomerInfo customerInfo = (CustomerInfo) session.getAttribute("user");
         if(customerInfo == null) {
            return "redirect:/login";
        }
        Feedback feedback = feedbackService.findMyFeedback(customerInfo);
         if(feedback == null) {
             feedback  = new Feedback();
             feedback.setNoStars(5);
             feedback.setFeedback("");
         }

         model.addAttribute("feedback", feedback);
        return "Customer/feedback";
    }

    @PostMapping("/feedback")
    public String createFeedback(@RequestParam("rating") int rating, @RequestParam("review") String review, HttpSession session) {
        CustomerInfo customerInfo = (CustomerInfo) session.getAttribute("user");
        if(customerInfo == null)
            return "redirect:/login";

        Feedback feedback = feedbackService.findMyFeedback(customerInfo);
        if(feedback == null){
            feedback = new Feedback();
        }
        feedback.setNoStars(rating);
        feedback.setFeedback(review);
        feedback.setCustomerInfo(customerInfo);
        feedbackService.saveFeedback(feedback);
        return "redirect:/home";
    }
}
