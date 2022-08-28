package com.example.springdemo.repository;

import com.example.springdemo.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    Optional<Variant> findByLabInfoIdAndStudentId(Long labInfoId, Long studentId);
}
