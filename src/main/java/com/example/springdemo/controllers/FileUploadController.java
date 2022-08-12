package com.example.springdemo.controllers;

import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LabInfoRepository labInfoRepository;

    @PostMapping(path = "labid{lab_info_id}")
    public String uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes, @PathVariable("lab_info_id") Long labId,
            Model model) {
        String path = labInfoRepository.getReferenceById(labId).getCourse().getName() + "/labid" + labId;
        model.addAttribute("id",labId);
        fileStorageService.storeFile(file, path, labId);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/labid"+labId;
    }
    @GetMapping(path="labid{lab_info_id}")
    public String view(){
        return "lab";
    }
}
