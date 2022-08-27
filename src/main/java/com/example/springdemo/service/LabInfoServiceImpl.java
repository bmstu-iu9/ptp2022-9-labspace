package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
