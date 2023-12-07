package com.example.fcs;

import com.example.fcs.servicies.AnalysisService;
import com.example.fcs.servicies.FeedbackService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
                                   @RequestParam String country, HttpServletRequest request){
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(60*5);
        cookie.setPath("/");
        Cookie cookie1 = new Cookie("age", String.valueOf(age));
        cookie1.setMaxAge(60*5);
        cookie1.setPath("/");
        Cookie cookie2 = new Cookie("country", country.trim().toLowerCase());
        cookie2.setMaxAge(60*5);
        cookie2.setPath("/");
        return "redirect:/feedback";
    }
    @PostMapping("/get-started/admin-check")
    public String adminCheck(@RequestParam String password){
        if(password.equals("1111")){
            return "redirect:/admin";
        }
        passwordMessage = "Wrong admin password";
        return "redirect:/get-started";
    }
}
