package com.example.springdemo.repository;

import com.example.springdemo.entity.GradesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GradesListRepository extends JpaRepository<GradesList, Long> {
    @Query("select sum (grades.mark) from GradesList grades where grades.submitLab.user.id = ?1")
    int getTotalPointsByStudentId(Long id);

}
