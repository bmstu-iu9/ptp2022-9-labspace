package com.example.springdemo.service;

import com.example.springdemo.repository.GradesListRepository;
import com.example.springdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GradesListService {
    private final GradesListRepository gradesListRepository;
@Autowired
    public GradesListService(GradesListRepository gradesListRepository) {
        this.gradesListRepository = gradesListRepository;
    }

    public int getTotalPointsbyStudentId(Long id) {
        return gradesListRepository.getTotalPointsByStudentId(id);
    }
}
