package com.example.springdemo.controllers;

import com.example.springdemo.entity.Course;
import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.CourseRepository;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class LabInfoUploadController {
    @Autowired
    private VariantService variantService;

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    GrouppRepository grouppRepository;
    @Autowired
    LabInfoService labInfoService;

    @Autowired
    UserService userService;

    @Autowired
    DeadlineService deadlineService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(value = "/admin/upload_lab")
    public String uploadlab(Model model) {
        LabInfo labInfo = new LabInfo();
        authenticationService.addNameAndGroupToModel(model);
        model.addAttribute("labInfo", labInfo);
        Iterable<Groupp> groupList = grouppRepository.findAll();
        Iterable<Course> courses = courseRepository.findAll();
        model.addAttribute("coursesList", courses);
        model.addAttribute("groupList", groupList);
        return "teacher_lab";
    }

    @PostMapping(value = "/main/upload_lab")
    public String uploadLab(@Valid LabInfo labInfo,
                          @RequestParam(name = "filee") MultipartFile file,
                          @RequestParam(name = "variants") int count,
                          HttpServletRequest request) throws ParseException {

        labInfoService.uploadLab(labInfo, file, request);
        variantService.randomizeVariants(count, labInfo);
        return "redirect:/";
    }
}
