package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import java.util.Date;
import java.util.HashMap;
public interface LabInfoService {
    void uploadLab(LabInfo labInfo);
    HashMap<Date, Integer> getDeadlinesByLabId(Long id);
}
