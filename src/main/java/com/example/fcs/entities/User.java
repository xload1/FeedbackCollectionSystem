package com.example.fcs.entities;

import jakarta.persistence.Entity;
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
public class User {
    @Id
    int user_id;
    String username;
    int age;
    String country;

    public User(String username, int age, String country) {
        this.username = username;
        this.age = age;
        this.country = country;
    }
}
