package com.example.springdemo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
     void storeFile(MultipartFile file, String path, Long labId);

     Resource loadAsResource(Long userId, Long labId);

}
