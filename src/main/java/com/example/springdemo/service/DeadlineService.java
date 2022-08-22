package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
public interface DeadlineService {
    void saveDeadlines(HttpServletRequest request, LabInfo labInfo) throws ParseException;
}
