package com.example.springdemo.controllers;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.SubmitLabRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.LabInfoService;
import com.example.springdemo.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class MainController {

    @Autowired
    LabInfoRepository labInfoRepository;
    @Autowired
    LabInfoService labInfoService;

    @Autowired
    UserService userService;

    @Autowired
    SubmitLabRepository submitLabRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/")
    public String view(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        String role =  authResult.getAuthorities().toString();
        if (authResult.getName().length() == 0){
            return "redirect:auth/login";
        }
        if(role.contains("ADMIN")){
            //response.sendRedirect(response.encodeRedirectURL( "/admin/" + request.getContextPath()));
            return "redirect:/admin/index";
        }
        else if(role.contains("USER")) {
            //response.sendRedirect(response.encodeRedirectURL("/user/" + request.getContextPath()));
            return "redirect:/main/index";
        }
        return "redirect:/main/index";
    }

    @GetMapping("/main/minor")
    public String index(HttpServletRequest request, Model model) {
        String username = authenticationService.getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
            List<SubmitLab> submit_labs = submitLabRepository.findByUserId(user.getId());
            model.addAttribute("submit_labs", submit_labs);
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
        return "minor";
    }

    @NotNull


    @Autowired
    private DeadlineRepository deadlineRepository;

    @GetMapping("/main/index")
    public String home2(Model model) {
        String username = authenticationService.getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
            Set<LabInfo> labs = labInfoService.getAvalibleLabs(user);
            model.addAttribute("labs", labs);
            model.addAttribute("deadlineRepository", deadlineRepository);
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
        return "index";
    }

    @GetMapping("/main/teacher_lab")
    public String teacher_lab( Model model) {
        authenticationService.addNameAndGroupToModel(model);
        return "teacher_lab";
    }
}
