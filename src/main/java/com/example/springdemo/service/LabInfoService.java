package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface LabInfoService {
    void uploadLab(LabInfo labInfo);
    HashMap<Date, Integer> getDeadlinesByLabId(Long id);
}
