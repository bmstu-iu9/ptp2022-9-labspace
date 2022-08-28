package com.example.springdemo.repository;

import com.example.springdemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String courseName);

    Optional<Course> findById(Long id);
}
