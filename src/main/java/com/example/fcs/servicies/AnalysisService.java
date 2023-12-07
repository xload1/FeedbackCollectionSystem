package com.example.fcs.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    FeedbackService feedbackService;
    @Autowired
    public AnalysisService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
}
