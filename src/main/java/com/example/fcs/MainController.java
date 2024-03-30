package com.example.fcs;

import com.example.fcs.additional.UserFeedback;
import com.example.fcs.entities.Feedback;
import com.example.fcs.entities.Users;
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

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String redirect() {
        return "redirect:/get-started";
    }

    @GetMapping("/get-started")
    public String getStarted(Model model) {
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
                                   @RequestParam String country, HttpServletResponse response) {
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(60 * 5);
        cookie.setPath("/");
        Cookie cookie1 = new Cookie("age", String.valueOf(age));
        cookie1.setMaxAge(60 * 5);
        cookie1.setPath("/");
        Cookie cookie2 = new Cookie("country", country.trim().toLowerCase());
        cookie2.setMaxAge(60 * 5);
        cookie2.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        return "redirect:/feedback";
    }

    @PostMapping("/get-started/admin-check")
    public String adminCheck(@RequestParam String password) {
        if (password.equals("1111")) {
            isAdmin = true;
            return "redirect:/analytics";
        }
        passwordMessage = "Wrong admin password";
        return "redirect:/get-started";
    }

    @GetMapping("/feedback")
    public String feedback(HttpServletRequest request, Model model) {
        boolean isUser = !cookieSearch(request, "username").equals("");
        model.addAttribute("isUser", isUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("feedbackMessage", feedbackMessage);
        return "feedback";
    }

    @PostMapping("/feedback/submit")
    public String feedbackSubmit(@RequestParam String text, @RequestParam(required = false) int rating,
                                 @RequestParam(required = false) int convenience, @RequestParam(required = false, defaultValue = "false") boolean satisfied,
                                 @RequestParam(required = false, defaultValue = "false") boolean anonymous, HttpServletRequest request) {
        if (cookieSearch(request, "username").equals("")) {
            feedbackMessage = "You are not logged in";
            return "redirect:/feedback";
        }
        if(convenience == 0 || rating == 0) {
            feedbackMessage = "Please, specify rating and convenience";
            return "redirect:/feedback";
        }
        if(feedbackService.getUsersFeedbacks().stream().filter(userFeedback -> userFeedback.getUsername().equals(cookieSearch(request, "username"))).toList().size() > 0) {
            feedbackMessage = "You have already left feedback";
            return "redirect:/feedback";
        }
        try {
            feedbackService.saveFeedback(new Feedback(text, rating, convenience, satisfied,
                    anonymous));
            feedbackService.saveUser(new Users(cookieSearch(request, "username"),
                    Integer.parseInt(cookieSearch(request, "age")), cookieSearch(request, "country")));
            feedbackMessage = "Thank you for your feedback!";
            System.out.println(new Users(cookieSearch(request, "username"),
                    Integer.parseInt(cookieSearch(request, "age")), cookieSearch(request, "country")));
        } catch (Exception e) {
            feedbackMessage = "Something went wrong";
        }
        return "redirect:/feedback";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) String sort) {
        List<UserFeedback> feedbacks = feedbackService.getUsersFeedbacks();
        if(sort != null) {
            switch (sort) {
                case "Username" -> {
                    feedbacks.sort(Comparator.comparing(UserFeedback::getUsername));
                }
                case "Age" -> {
                    feedbacks.sort(Comparator.comparing(UserFeedback::getAge));
                }
                case "Feedback" -> {
                    feedbacks.sort(Comparator.comparingInt(e -> e.getFeedback().length()));
                }
                case "Country" -> {
                    feedbacks.sort(Comparator.comparing(UserFeedback::getCountry));
                }
                case "Rating" -> {
                    feedbacks.sort(Comparator.comparing(UserFeedback::getRating));
                }
                case "Convenience" -> {
                    feedbacks.sort(Comparator.comparing(UserFeedback::getConvenience));
                }
                case "Satisfied" -> {
                    feedbacks.sort(Comparator.comparing(UserFeedback::isSatisfied));
                }
            }
        }
        if(!isAdmin){
            model.addAttribute("feedbacks", feedbacks.stream().filter(userFeedback -> !userFeedback.isAnonymous()).toList());
        } else {
            model.addAttribute("feedbacks", feedbacks);
        }
        model.addAttribute("isAdmin", isAdmin);
        return "list";
    }
    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("averageRating", new DecimalFormat("#.#").format(analysisService.getAverageRating()) );
        model.addAttribute("averageConvenience", new DecimalFormat("#.#").format(analysisService.getAverageConvenience()));
        model.addAttribute("satisfiedPercentage", analysisService.getSatisfactionPercentage());
        model.addAttribute("mostSatisfiedCountry", analysisService.getMostSatisfiedCountry());
        model.addAttribute("bestAge", new DecimalFormat("##.#").format(analysisService.getBestAge()));
        model.addAttribute("top3countries", analysisService.getCountriesMap().entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(Map.Entry::getValue)))
                .limit(3).collect(Collectors.toList()));
        return "analytics";
    }
}
