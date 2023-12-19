package com.example.fcs;

import com.example.fcs.additional.UserFeedback;
import com.example.fcs.servicies.AnalysisService;
import com.example.fcs.servicies.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnalysisServiceTest {
    private AnalysisService analysisService;
    private FeedbackService feedbackService;
    List<UserFeedback> testList = List.of(new UserFeedback("", 61, "Ukraine", "", 4 , 3, true, false),
            new UserFeedback("", 21, "Poland", "", 5 , 3, false, false),
            new UserFeedback("", 101, "Madagascar", "", 3 , 3, true, false),
            new UserFeedback("", 45, "Ukraine", "", 4 , 3, false, false),
            new UserFeedback("", 78, "Madagascar", "", 4 , 5, true, false),
            new UserFeedback("", 26, "Madagascar", "", 2 , 3, false, false),
            new UserFeedback("", 26, "Madagascar", "", 4 , 3, false, false),
            new UserFeedback("", 90, "Ukraine", "", 1 , 3, true, false));
    @BeforeEach
    public void setUp() {
        feedbackService = mock(FeedbackService.class);
        analysisService = new AnalysisService(feedbackService);
    }
    @Test
    public void testGetAverageRating() {
        when(feedbackService.getUsersFeedbacks()).thenReturn(testList);
           assert(analysisService.getAverageRating()==3.375);
    }
    @Test
    public void testGetCountriesMap() {
        when(feedbackService.getUsersFeedbacks()).thenReturn(testList);
        assert(analysisService.getCountriesMap().get("Ukraine")==3);
        assert(analysisService.getCountriesMap().get("Poland")==1);
        assert(analysisService.getCountriesMap().get("Madagascar")==4);
    }
    @Test
    public void testGetAverageConvenience() {
        when(feedbackService.getUsersFeedbacks()).thenReturn(testList);
        assert(analysisService.getAverageConvenience()==3.25);
    }
    @Test
    public void testGetSatisfactionPercentage() {
        when(feedbackService.getUsersFeedbacks()).thenReturn(testList);
        assert(analysisService.getSatisfactionPercentage()==50);
    }
    @Test
    public void testGetMostSatisfiedCountry() {
        when(feedbackService.getUsersFeedbacks()).thenReturn(testList);
        assert(analysisService.getMostSatisfiedCountry().equals("Ukraine"));
    }
    @Test
    public void testGetBestAge() {
        when(feedbackService.getUsersFeedbacks()).thenReturn(testList);
        assert(analysisService.getBestAge()==82.5);
    }
}
