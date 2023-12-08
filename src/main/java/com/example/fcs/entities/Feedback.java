package com.example.fcs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int user_id;
    String text;
    int rating;
    int convenience;
    boolean satisfied;
    boolean anonymous;

    public Feedback(String text, int rating, int convenience, boolean satisfied, boolean anonymous) {
        this.text = text;
        this.rating = rating;
        this.convenience = convenience;
        this.satisfied = satisfied;
        this.anonymous = anonymous;
    }
}
