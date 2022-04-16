package com.example.jwttoken.repository;

import com.example.jwttoken.model.WSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WSUserRepository extends JpaRepository<WSUser, Long> {
    WSUser findByUsername(String username);
}
