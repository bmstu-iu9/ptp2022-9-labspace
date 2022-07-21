package com.example.springdemo.repository;

import com.example.springdemo.entity.SubmitLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmitLabRepository extends JpaRepository<SubmitLab, Long> {
    List<SubmitLab> findByStudentEmail(String email);
    List<SubmitLab> findByStudentId(Long id);
}
