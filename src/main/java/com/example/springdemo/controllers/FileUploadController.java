package com.example.springdemo.controllers;

import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.FileStorageService;
import com.example.springdemo.service.SubmitLabService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
    private final FileStorageService fileStorageService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubmitLabService labService;
    @Autowired
    private LabInfoRepository labInfoRepository;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            return userService.getByEmail(authentication.getName());
    }

    @PostMapping(path = "labid{lab_info_id}")
    public String uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes, @PathVariable("lab_info_id") Long lab_id,
            Model model) {
        SubmitLab submitLab=labService.submitLab(getCurrentUser(),labInfoRepository.getReferenceById(lab_id));
        model.addAttribute("id",lab_id);
         fileStorageService.storeFile(file,submitLab.getSource());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/labid"+lab_id;
    }
    @GetMapping(path="labid{lab_info_id}")
    public String view(){
        return "lab";
    }
}
