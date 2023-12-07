package com.example.fcs.servicies;

import com.example.fcs.repos.FeedbackRepository;
import com.example.fcs.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    UserRepository userRepository;
    FeedbackRepository feedbackRepository;
    @Autowired
    public FeedbackService(UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }
}
