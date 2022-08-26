package com.example.springdemo.controllers;

import com.example.springdemo.entity.*;
import com.example.springdemo.repository.CourseRepository;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.DeadlineService;
import com.example.springdemo.service.FileStorageService;
import com.example.springdemo.service.LabInfoService;
import com.example.springdemo.service.UserService;
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
    LabInfoRepository labInfoRepository;
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "guest";
    }

    public void addNameAndGroupToModel(Model model){
        String username;
        username = getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp",user.getGroupp().getName());
        }
        else{
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
    }


    @GetMapping(value="/main/upload_lab")
    public String uploadlab(Model model){
        LabInfo labInfo=new LabInfo();
        addNameAndGroupToModel(model);
        model.addAttribute(courseRepository);
        model.addAttribute("labInfo", labInfo);
        Iterable<Groupp> groupList = grouppRepository.findAll();
        Iterable<Course> courses = courseRepository.findAll();
        model.addAttribute("coursesList", courses);
        model.addAttribute("groupList",groupList);
        return "teacher_lab";
    }

    @PostMapping(value = "/main/upload_lab")
    public String regUser( @Valid LabInfo labInfo,
                          @RequestParam(name = "filee") MultipartFile file, RedirectAttributes redirectAttributes,
                          Model model,
                          HttpServletRequest request) throws ServletException, ParseException {
            labInfo.setUploadDate(new Date(System.currentTimeMillis()));
          Optional<Course> tmpcourse = courseRepository.findById(Long.valueOf(request.getParameter("course_id")));

        Set<Groupp> groups = Arrays.stream(request.getParameterValues("groupss"))
                .map(id->grouppRepository.findById(Long.valueOf(id)))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
        labInfo.setGroupps(groups);
            labInfo.setSource("labs/");
            fileStorageService.storeFile(file,labInfo);
            tmpcourse.ifPresent(labInfo::setCourse);
            labInfoService.uploadLab(labInfo);
            groups.stream().peek(groupp -> groupp.getLabInfos().add(labInfo)).peek(groupp -> grouppRepository.save(groupp));
            deadlineService.saveDeadlines(request,labInfo);
        return "teacher_lab";
    }
}
