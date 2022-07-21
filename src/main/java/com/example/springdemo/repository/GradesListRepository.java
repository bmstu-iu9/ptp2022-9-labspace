package com.example.springdemo.repository;

import com.example.springdemo.entity.GradesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradesListRepository extends JpaRepository<GradesList, Long> {
}
