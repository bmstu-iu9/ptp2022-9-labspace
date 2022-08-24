package com.example.springdemo.service.impl;

import com.example.springdemo.entity.Deadline;
import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.LabInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LabInfoServiceImpl implements LabInfoService {
    @Autowired
    private DeadlineRepository deadlineRepository;
    @Autowired
    private LabInfoRepository labInfoRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void uploadLab(LabInfo labInfo) {
        labInfoRepository.save(labInfo);
    }

    @Override
    public HashMap<Date, Integer> getDeadlinesByLabId(Long id) {
        HashMap<Date, Integer> map = new HashMap<>();
        List<Deadline> deadlines = deadlineRepository.findAllByLabInfoId(id);
        for (Deadline elem : deadlines) {
            map.put(elem.getDeadlineDate(), elem.getMaxMark());
        }

        return map;
    }
}
