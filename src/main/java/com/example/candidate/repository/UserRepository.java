package com.example.candidate.repository;

import com.example.candidate.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.username = :username")
    long countByUsername(@Param("username") String username);

}
