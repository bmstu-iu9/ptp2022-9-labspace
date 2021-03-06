package com.example.springdemo.repository;

import com.example.springdemo.entity.LabInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabInfoRepository extends JpaRepository<LabInfo, Long> {
    List<LabInfo> findByIsVisible(Boolean isVisible);
}
