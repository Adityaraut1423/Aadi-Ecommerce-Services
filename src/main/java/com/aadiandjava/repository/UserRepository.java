package com.aadiandjava.repository; // Ensure this matches your actual package name

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aadiandjava.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA automatically writes the SQL query for this!
    User findByEmail(String email);
}