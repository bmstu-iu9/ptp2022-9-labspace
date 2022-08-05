package com.example.springdemo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
     void storeFile(MultipartFile file, String path, Long labId);

}
