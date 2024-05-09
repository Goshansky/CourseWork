package com.example.coursework.repository;

import com.example.coursework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);
}