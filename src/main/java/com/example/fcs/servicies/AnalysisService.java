package com.example.fcs.servicies;

import com.example.fcs.additional.UserFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AnalysisService {
    FeedbackService feedbackService;
    @Autowired
    public AnalysisService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
    public double getAverageRating(){
        return feedbackService.getUsersFeedbacks().stream().mapToInt(UserFeedback::getRating).average().orElse(0);
    }
    public HashMap<String, Integer> getCountriesMap(){
        HashMap<String, Integer> countries = new HashMap<>();
        feedbackService.getUsersFeedbacks().forEach(userFeedback -> {
            if (countries.containsKey(userFeedback.getCountry())){
                countries.put(userFeedback.getCountry(), countries.get(userFeedback.getCountry())+1);
            }else{
                countries.put(userFeedback.getCountry(), 1);
            }
        });
        return countries;
    }
    public double getAverageConvenience(){
        return feedbackService.getUsersFeedbacks().stream().mapToInt(UserFeedback::getConvenience).average().orElse(0);
    }
    public int getSatisfactionPercentage(){
        return (int) (feedbackService.getUsersFeedbacks().stream().filter(UserFeedback::isSatisfied).count()/
                (double)feedbackService.getUsersFeedbacks().size()*100);
    }
    public String getMostSatisfiedCountry(){
          return feedbackService.getUsersFeedbacks().stream().filter(UserFeedback::isSatisfied).collect(Collectors.groupingBy(UserFeedback::getCountry, Collectors.counting()))
                  .entrySet().stream().max(HashMap.Entry.comparingByValue()).map(HashMap.Entry::getKey).orElse("No satisfied countries");
    }
    public double getBestAge(){
        return feedbackService.getUsersFeedbacks().stream().filter(UserFeedback::isSatisfied).mapToInt(UserFeedback::getAge).average().orElse(0);
    }
}
