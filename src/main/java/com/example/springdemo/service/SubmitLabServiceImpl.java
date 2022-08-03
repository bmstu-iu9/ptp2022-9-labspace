package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.SubmitLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubmitLabServiceImpl implements SubmitLabService {
    @Autowired
    private SubmitLabRepository submitLabRepository;

    @Override
    public List<LabInfo> getCompleteLabsByEmail(String email) {
        List<SubmitLab> submitLabs = submitLabRepository.findByUserEmail(email);
        List<LabInfo> labs = new ArrayList<>();
        for(SubmitLab submitLab : submitLabs) {
            labs.add(submitLab.getLabInfo());
        }
        return labs;
    }

    @Override
    public List<LabInfo> getCompleteLabsByUserId(Long id) {
        List<SubmitLab> submitLabs = submitLabRepository.findByUserId(id);
        List<LabInfo> labs = new ArrayList<>();
        for(SubmitLab submitLab : submitLabs) {
            labs.add(submitLab.getLabInfo());
        }
        return labs;
    }

    @Override
    public SubmitLab submitLab(User user, LabInfo labInfo) {
        SubmitLab submitLab= new SubmitLab();
        submitLab.setLabInfo(labInfo);
        submitLab.setUser(user);
        Date date = new Date();
        submitLab.setSendDate(date);
        submitLab.setSource(labInfo.getCourse().getName() + "/labid" + labInfo.getId() );
        submitLabRepository.save(submitLab);
        return submitLab;
    }
}
