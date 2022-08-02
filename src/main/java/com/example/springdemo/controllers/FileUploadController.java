package com.example.springdemo.controllers;

import com.example.springdemo.response.UploadResponse;
import com.example.springdemo.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes) {
        String fileName = fileStorageService.storeFile(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/upload";
    }
    @GetMapping("/upload")
    public String view(){
        return "lab";
    }
}
