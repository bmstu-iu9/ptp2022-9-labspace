package com.example.springdemo.service.impl;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.LabInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabInfoServiceImpl implements LabInfoService {
    @Autowired
    private LabInfoRepository labInfoRepository;
    @Override
    public void uploadLab(LabInfo labInfo) {
    labInfoRepository.save(labInfo);
    }
}
