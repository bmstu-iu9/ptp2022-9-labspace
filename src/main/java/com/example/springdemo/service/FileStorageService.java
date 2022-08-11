package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
     void storeFile(MultipartFile file, String path, Long labId);
     void storeFile(MultipartFile file, LabInfo labInfo);
     Resource loadAsResource(Long userId, Long labId);

}
