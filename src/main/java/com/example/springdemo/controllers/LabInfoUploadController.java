package com.example.springdemo.controllers;

import com.example.springdemo.entity.Course;
import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.CourseRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.FileStorageService;
import com.example.springdemo.service.LabInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Controller
public class LabInfoUploadController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    LabInfoService labInfoService;
    @GetMapping(value="/uploadLab")
    public String uploadlab(Model model){
        LabInfo labInfo=new LabInfo();
        model.addAttribute("labInfo", labInfo);
        return "uploadLab";
    }

    @PostMapping(value = "/uploadLab")
    public String regUser( @Valid LabInfo labInfo,
                          @RequestParam(name = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes,
                          Model model,
                          HttpServletRequest request) throws ServletException {
            labInfo.setUploadDate(new Date(System.currentTimeMillis()));
            Optional<Course> tmpcourse = courseRepository.findById(Long.valueOf(request.getParameter("course_id")));
            labInfo.setSource("labs/");
            fileStorageService.storeFile(file,labInfo);
            tmpcourse.ifPresent(labInfo::setCourse);
            labInfoService.uploadLab(labInfo);
        return "uploadLab";
    }
}
