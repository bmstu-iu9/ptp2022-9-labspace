package com.example.springdemo.repository;

import com.example.springdemo.entity.Groupp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrouppRepository extends JpaRepository<Groupp, Long> {
    Groupp findByName(String name);
}
