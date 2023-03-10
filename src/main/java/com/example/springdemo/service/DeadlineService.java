package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

public interface DeadlineService {
    void saveDeadlines(HttpServletRequest request, LabInfo labInfo) throws ParseException;

    int getMarkByDate(LabInfo lab, Date date);
}
