package com.example.springdemo.service;

public interface GradesListService {
    int getTotalPointsByStudentId(Long id);

    int getPointsByStudentAndLab(String email, Long labInfoId);
}
