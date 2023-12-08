package com.example.fcs;

import com.example.fcs.servicies.AnalysisService;
import com.example.fcs.servicies.FeedbackService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    AnalysisService analysisService;
    FeedbackService feedbackService;
    String passwordMessage = "";
    boolean isAdmin = false;
    String feedbackMessage = "";
    @Autowired
    public MainController(AnalysisService analysisService, FeedbackService feedbackService) {
        this.analysisService = analysisService;
        this.feedbackService = feedbackService;
    }
    @GetMapping("/")
    public String redirect(){
        return "redirect:/get-started";
    }
    @GetMapping("/get-started")
    public String getStarted(Model model){
        model.addAttribute("passwordMessage", passwordMessage);
        System.out.println(passwordMessage);
        return "get-started";
    }
    public String cookieSearch(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
    @PostMapping("/get-started/submit")
    public String getStartedSubmit(@RequestParam String username, @RequestParam int age,
                                   @RequestParam String country, HttpServletResponse response){
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(60*5);
        cookie.setPath("/");
        Cookie cookie1 = new Cookie("age", String.valueOf(age));
        cookie1.setMaxAge(60*5);
        cookie1.setPath("/");
        Cookie cookie2 = new Cookie("country", country.trim().toLowerCase());
        cookie2.setMaxAge(60*5);
        cookie2.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "redirect:/feedback";
    }
    @PostMapping("/get-started/admin-check")
    public String adminCheck(@RequestParam String password){
        if(password.equals("1111")){
            isAdmin = true;
            return "redirect:/analytics";
        }
        passwordMessage = "Wrong admin password";
        return "redirect:/get-started";
    }
    @GetMapping("/feedback")
    public String feedback(HttpServletRequest request, Model model){
        boolean isUser = !cookieSearch(request, "username").equals("");
        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("feedbackMessage", feedbackMessage);
        return "feedback";
    }
    @GetMapping("/feedback/submit")
    public String feedbackSubmit(@RequestParam String text, @RequestParam int rating,
                                 @RequestParam int convenience, @RequestParam boolean satisfied,
                                 @RequestParam boolean anonymous, HttpServletRequest request){
        try {
            feedbackService.saveFeedback(new com.example.fcs.entities.Feedback(text, rating, convenience, satisfied, anonymous));
            feedbackService.saveUser(new com.example.fcs.entities.User(cookieSearch(request, "username"),
                    Integer.parseInt(cookieSearch(request, "age")), cookieSearch(request, "country")));
            feedbackMessage = "Thank you for your feedback!";
        } catch (Exception e) {
            feedbackMessage = "Something went wrong";
        }
        return "redirect:/feedback";
    }
}
