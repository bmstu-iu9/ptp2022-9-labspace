package com.example.springdemo.controllers;

import com.example.springdemo.response.UploadResponse;
import com.example.springdemo.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file
    ) {
        String fileName = fileStorageService.storeFile(file);

        UploadResponse uploadResponse = new UploadResponse(fileName);

        return ResponseEntity.ok().body(uploadResponse);
    }
}
