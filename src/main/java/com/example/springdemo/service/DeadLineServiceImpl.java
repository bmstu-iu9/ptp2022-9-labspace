package com.example.springdemo.service;

import com.example.springdemo.entity.Deadline;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DeadLineServiceImpl implements DeadlineService {
    @Autowired
    DeadlineRepository deadlineRepository;

    @Override
    public void saveDeadlines(HttpServletRequest request, LabInfo labInfo) throws ParseException {
        Deadline dl1 = new Deadline();
        Deadline dl2 = new Deadline();
        Deadline dl3 = new Deadline();
        dl1.setDeadlineDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date1")));
        dl2.setDeadlineDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date2")));
        dl3.setDeadlineDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date3")));
        dl1.setMaxMark(Integer.valueOf(request.getParameter("mark1")));
        dl2.setMaxMark(Integer.valueOf(request.getParameter("mark2")));
        dl3.setMaxMark(Integer.valueOf(request.getParameter("mark3")));
        dl1.setLabInfo(labInfo);
        dl2.setLabInfo(labInfo);
        dl3.setLabInfo(labInfo);
        deadlineRepository.save(dl1);
        deadlineRepository.save(dl2);
        deadlineRepository.save(dl3);
    }
}
