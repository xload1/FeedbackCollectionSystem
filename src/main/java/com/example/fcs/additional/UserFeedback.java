package com.example.fcs.additional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFeedback {
    String username;
    int age;
    String country;
    String feedback;
    int rating;
    int convenience;
    boolean satisfied;
    boolean anonymous;
}
