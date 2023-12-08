package com.example.fcs.repos;

import com.example.fcs.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer>{
}
