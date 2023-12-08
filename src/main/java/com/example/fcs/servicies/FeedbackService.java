package com.example.fcs.servicies;

import com.example.fcs.additional.UserFeedback;
import com.example.fcs.entities.Feedback;
import com.example.fcs.entities.Users;
import com.example.fcs.repos.FeedbackRepository;
import com.example.fcs.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {
    UserRepository userRepository;
    FeedbackRepository feedbackRepository;
    @Autowired
    public FeedbackService(UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }
    public void saveFeedback(Feedback feedback){
        feedbackRepository.save(feedback);
    }
    public void saveUser(Users user){
        userRepository.save(user);
    }
    public List<UserFeedback> getUsersFeedbacks(){
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<Users> users = userRepository.findAll();
        List<UserFeedback> userFeedbacks = new ArrayList<>();
        for (int i = 0; i < feedbacks.size(); i++) {
            if (feedbacks.get(i)!=null&&users.get(i)!=null){
                UserFeedback userFeedback = new UserFeedback(users.get(i).getUsername(),users.get(i).getAge(),
                        users.get(i).getCountry(), feedbacks.get(i).getText(), feedbacks.get(i).getRating(),
                        feedbacks.get(i).getConvenience(),
                        feedbacks.get(i).isSatisfied(), feedbacks.get(i).isAnonymous());
                userFeedbacks.add(userFeedback);
            }
        }
        return userFeedbacks;
    }
}
