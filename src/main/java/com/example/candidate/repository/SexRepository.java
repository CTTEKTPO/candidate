package com.example.candidate.repository;

import com.example.candidate.model.Sex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SexRepository extends JpaRepository<Sex, Long> {
}
