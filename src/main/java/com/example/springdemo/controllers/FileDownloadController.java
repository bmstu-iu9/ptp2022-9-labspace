package com.example.springdemo.controllers;

import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileDownloadController {
    @Autowired
    private LabInfoRepository labInfoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("main/labid{labId}/download/userid{userId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long labId, @PathVariable Long userId) {

        Resource file = fileStorageService.loadAsResource(labId, userId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
