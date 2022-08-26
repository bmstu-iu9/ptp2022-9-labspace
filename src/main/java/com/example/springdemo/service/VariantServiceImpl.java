package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.LabInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariantServiceImpl implements VariantService {

    @Autowired
    private LabInfoRepository labInfoRepository;

    @Override
    public void randomizeVariants(int n, LabInfo labInfo) {

    }
}
