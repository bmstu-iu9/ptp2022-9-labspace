package com.example.springdemo.repository;

import com.example.springdemo.entity.Groupp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrouppRepository extends JpaRepository<Groupp, Long> {
    Optional<Groupp> findByName(String name);
}
