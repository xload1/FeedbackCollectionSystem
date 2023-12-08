package com.example.fcs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int user_id;
    String username;
    int age;
    String country;

    public Users(String username, int age, String country) {
        this.username = username;
        this.age = age;
        this.country = country;
    }
}
