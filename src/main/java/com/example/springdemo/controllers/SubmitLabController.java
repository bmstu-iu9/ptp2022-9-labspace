package com.example.springdemo.controllers;

import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.FileStorageService;
import com.example.springdemo.service.GradesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;

@Controller
public class SubmitLabController {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LabInfoRepository labInfoRepository;
    @Autowired
    private GradesListService gradesListService;
    @Autowired
    private DeadlineRepository deadlineRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "main/lab_id{lab_info_id}")
    public String uploadFile(
            @RequestParam(name = "filee", required = false) MultipartFile file,
            @PathVariable("lab_info_id") Long labId, Model model) {
        String path = labInfoRepository.getReferenceById(labId).getCourse().getName() + "/labid" + labId;
        model.addAttribute("id", labId);
        fileStorageService.storeFile(file, path, labId);
        return "redirect:/";
    }

    @GetMapping(path = "main/lab_id{lab_info_id}")
    public String view(Model model, @PathVariable("lab_info_id") Long lab_id) {
        model.addAttribute("lab_info", labInfoRepository.getReferenceById(lab_id));
        model.addAttribute("grade", gradesListService.getPointsByStudentAndLab(authenticationService.getCurrentUsername(), lab_id));
        model.addAttribute("deadlines", deadlineRepository.findAllByLabInfoId(lab_id));
        SimpleDateFormat formatdayMonth = new SimpleDateFormat("dd.MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        model.addAttribute("formatdayMonth", formatdayMonth);
        model.addAttribute("formatYear", formatYear);
        return "templs/templateOfUploadLab";
    }

    @GetMapping(path = "main/lab_id{lab_info_id}/download")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long lab_info_id) {
        Resource file = fileStorageService.loadAsResource(lab_info_id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
