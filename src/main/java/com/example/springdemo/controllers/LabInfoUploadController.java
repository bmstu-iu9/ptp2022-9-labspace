package com.example.springdemo.controllers;

import com.example.springdemo.entity.*;
import com.example.springdemo.repository.CourseRepository;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class LabInfoUploadController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    GrouppRepository grouppRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    LabInfoService labInfoService;
    @Autowired
    UserService userService;
    @Autowired
    DeadlineService deadlineService;
    @Autowired
    AuthenticationService authenticationService;
    @GetMapping(value = "/main/upload_lab")
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
    public String regUser(@Valid LabInfo labInfo,
                          @RequestParam(name = "filee") MultipartFile file,
                          HttpServletRequest request) throws ParseException {
        labInfoService.uploadLab(labInfo,file,request);
        return "teacher_lab";
    }
}
