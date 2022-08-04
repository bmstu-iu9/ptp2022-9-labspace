package com.example.springdemo.service;

import com.example.springdemo.entity.SubmitLab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public interface FileStorageService {
     void storeFile(MultipartFile file, SubmitLab lab);

}
