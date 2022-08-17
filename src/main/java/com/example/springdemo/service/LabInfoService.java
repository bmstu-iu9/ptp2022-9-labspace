package com.example.springdemo.service;

import java.util.Date;
import java.util.HashMap;

public interface LabInfoService {
    HashMap<Date, Integer> getDeadlinesByLabId(Long id);
}
