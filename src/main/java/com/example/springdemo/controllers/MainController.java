package com.example.springdemo.controllers;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.CourseRepository;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.RequestService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class MainController {
    //function of getting directory
    public static List<File> files(String dirname) {
        if (dirname == null) {
            return Collections.emptyList();
        }

        File dir = new File(dirname);
        if (!dir.exists()) {
            return Collections.emptyList();
        }

        if (!dir.isDirectory()) {
            return Collections.singletonList(dir);
        }

        return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .collect(Collectors.toList());
    }
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

    @Autowired
    private RequestService requestService;

    @Autowired
    private LabInfoRepository labInfoRepository;

    @Autowired
    UserService userService;
    @GetMapping("/")
    public String view(){
        return "redirect:/main/index";
    }
    @GetMapping("/main/minor")
    public String index(HttpServletRequest request, Model model) {
        addNameAndGroupToModel(model);
        return "minor";
    }

    @Autowired
    private DeadlineRepository deadlineRepository;

    @Autowired
    private CourseRepository courseRepository;


    @GetMapping("/main/index")
    public String home2(HttpServletRequest request, Model model) {
        String username;
        username = getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp",user.getGroupp().getName());
            //List<LabInfo> labs = labInfoRepository.findByIsVisible(Boolean.TRUE);
            List<LabInfo> labs = labInfoRepository.findByIsVisibleAndGroupId(Boolean.TRUE, user.getGroupp().getId());
            model.addAttribute("labs", labs);
            model.addAttribute("deadlineRepository", deadlineRepository);
            model.addAttribute("courseRepository", courseRepository);
        }
        else{
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
        return "index";
    }

    @GetMapping("/main/teacher_lab")
    public String teacher_lab(HttpServletRequest request, Model model) {
        addNameAndGroupToModel(model);
        return "teacher_lab";
    }

    @GetMapping("/main/upload_lab")
    public String upload_lab(HttpServletRequest request, Model model) {
        addNameAndGroupToModel(model);
        return "templs/templateOfUploadLab";
    }
}
