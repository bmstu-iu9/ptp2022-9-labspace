package com.example.springdemo.repository;

import com.example.springdemo.entity.LabInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public interface LabInfoRepository extends JpaRepository<LabInfo, Long> {
    List<LabInfo> findByIsVisible(Boolean isVisible);
}
