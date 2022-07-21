package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;

import java.util.List;

public interface SubmitLabService {
    List<LabInfo> getCompleteLabsByEmail(String email);

    List<LabInfo> getCompleteLabsByStudentId(Long id);
}
