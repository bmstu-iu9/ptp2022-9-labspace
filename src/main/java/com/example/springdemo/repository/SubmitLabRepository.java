package com.example.springdemo.repository;

import com.example.springdemo.entity.SubmitLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmitLabRepository extends JpaRepository<SubmitLab, Long> {
    List<SubmitLab> findByUserEmail(String email);

    List<SubmitLab> findByUserId(Long id);

    Integer getMarkById(Long id);

    Optional<SubmitLab> findByUserIdAndLabInfoId(Long userId, Long labInfoId);

    List<SubmitLab> findAllByUserId(Long userId);

    List<SubmitLab> findAll();

    List<SubmitLab> findAllByUserIdAndMarkGreaterThan(Long user_id, int mark);

}
