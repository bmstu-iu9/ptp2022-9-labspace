package com.example.springdemo.controllers.admin;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.SubmitLabRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;



@Controller
public class AdminMainController {

    @Autowired
    LabInfoRepository labInfoRepository;

    @Autowired
    UserService userService;

    @Autowired
    SubmitLabRepository submitLabRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/admin/index")
    public String index(HttpServletRequest request, Model model) {
        return "index";
    }

    @Autowired
    private DeadlineRepository deadlineRepository;

    @GetMapping("/admin/minor")
    public String home2(Model model) {
        return "minor";
    }

}
