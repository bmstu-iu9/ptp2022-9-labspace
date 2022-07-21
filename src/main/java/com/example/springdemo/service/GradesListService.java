package com.example.springdemo.service;

import com.example.springdemo.repository.GradesListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradesListService {
    @Autowired
    private GradesListRepository gradesListRepository;

    public int getTotalPointsByStudentId(Long id) {
        return gradesListRepository.getTotalPointsByStudentId(id);
    }
}
