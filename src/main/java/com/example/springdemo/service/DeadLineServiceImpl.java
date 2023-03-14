package com.example.springdemo.service;

import com.example.springdemo.entity.Deadline;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DeadLineServiceImpl implements DeadlineService {
    @Autowired
    DeadlineRepository deadlineRepository;

    @Override
    public void saveDeadlines(HttpServletRequest request, LabInfo labInfo) throws ParseException {
        Deadline dl1 = new Deadline();
        Deadline dl2 = new Deadline();
        Deadline dl3 = new Deadline();
        try {
            dl1.setDeadlineDate(new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(request.getParameter("date1") + request.getParameter("time1")));
            dl1.setLabInfo(labInfo);
            dl1.setMaxMark(Integer.valueOf(request.getParameter("mark1")));
            deadlineRepository.save(dl1);
        } catch (ParseException ignored) {
        }
        try {
            dl2.setDeadlineDate(new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(request.getParameter("date2") + request.getParameter("time2")));
            dl2.setLabInfo(labInfo);
            dl2.setMaxMark(Integer.valueOf(request.getParameter("mark2")));
            deadlineRepository.save(dl2);
        } catch (ParseException ignored) {
        }
        try {
            dl3.setDeadlineDate(new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(request.getParameter("date3") + request.getParameter("time3")));
            dl3.setLabInfo(labInfo);
            dl3.setMaxMark(Integer.valueOf(request.getParameter("mark3")));
            deadlineRepository.save(dl3);
        } catch (ParseException ignored) {
        }
    }

    @Override
    public int getMarkByDate(LabInfo lab, Date date) {
        List<Deadline> deadlines = deadlineRepository.findAllByLabInfoId(lab.getId());
        Collections.sort(deadlines);
        int mark = 0;
        for (Deadline d : deadlines) {
            if (d.getDeadlineDate().after(date)) {
                mark = d.getMaxMark();
                break;
            }
        }
        return mark;
    }

    @Override
    public int getDeadlineNumber(LabInfo lab, Date date) {
        List<Deadline> deadlines = deadlineRepository.findAllByLabInfoId(lab.getId());
        Collections.sort(deadlines);
        int number = 1;
        for (Deadline d : deadlines) {
            if (d.getDeadlineDate().after(date)) break;
            else number++;
        }
        if (number > deadlines.size()) {
            return -1;
        } else {
            return number;
        }
    }
}
