package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;

public interface VariantService {

    void randomizeVariants(int n, LabInfo labInfo);

    int getVariantByLabInfoIdAndStudentId(Long lab_info_id, Long student_id);
}
