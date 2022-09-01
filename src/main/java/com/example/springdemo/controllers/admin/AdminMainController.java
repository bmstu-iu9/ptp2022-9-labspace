package com.example.springdemo.controllers.admin;

import com.example.springdemo.entity.*;
import com.example.springdemo.repository.*;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.LabInfoService;
import com.example.springdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Controller
public class AdminMainController {

    Logger logger = LoggerFactory.getLogger(AdminMainController.class);

    @Autowired
    LabInfoRepository labInfoRepository;

    @Autowired
    UserService userService;

    @Autowired
    SubmitLabRepository submitLabRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    GrouppRepository grouppRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    LabInfoService labInfoService;

    @GetMapping("/admin/index")
    public String index(HttpServletRequest request, Model model) {
        String username = authenticationService.getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
            List<LabInfo> labs = labInfoRepository.findAll();
            Collections.reverse(labs);
            model.addAttribute("labs", labs);
            Iterable<Groupp> groupList = grouppRepository.findAll();
            Iterable<Course> courses = courseRepository.findAll();
            model.addAttribute("coursesList", courses);
            model.addAttribute("groupList", groupList);
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
        return "adminTemp/adminIndex";
    }

    @PostMapping("/admin/index")
    public String changeVisibility(HttpServletRequest request) {
        String val = request.getParameter("Do");
        Long id = Long.valueOf(val.substring(1));
        LabInfo lab = labInfoRepository.findById(id).get();
        if (val.charAt(0) == 't'){
            logger.trace("Set visibitity of lab id " + val.substring(1) + " true");
            lab.setIsVisible(true);
        }
        else{
            logger.trace("Set visibitity of lab id " + val.substring(1) + " false");
            lab.setIsVisible(false);
        }
        labInfoRepository.save(lab);
        return "redirect:/admin/index";
    }


    @Autowired
    private DeadlineRepository deadlineRepository;
    @GetMapping("/admin/minor")
    public String home2(Model model) {
        String username = authenticationService.getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
            List<SubmitLab> submit_labs = submitLabRepository.findAllNotChecked();
            model.addAttribute("submit_labs", submit_labs);
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
        return "adminTemp/adminMinor";
    }

    @GetMapping("/admin/gradedLabs")
    public String home3(Model model) {
        String username = authenticationService.getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
            List<SubmitLab> submit_labs = submitLabRepository.findAllChecked();
            Collections.reverse(submit_labs);
            model.addAttribute("submit_labs", submit_labs);
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
        return "adminTemp/adminGraded";
    }

}
