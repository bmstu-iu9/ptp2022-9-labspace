package com.example.springdemo.controllers;

import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.SubmitLabRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.LabInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ProfilePageController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    SubmitLabRepository submitLabRepository;
    @Autowired
    LabInfoService labInfoService;

    @GetMapping("/main/profile")
    public String getUser( Model model) {
        User user = authenticationService.getCurrentUser();
        model.addAttribute("user", user);
        List<SubmitLab> completedLabs = submitLabRepository.findAllByUserIdAndMarkGreaterThan(user.getId(), 0);
        int sum = 0;
        for (SubmitLab lab: completedLabs){
            sum+= lab.getMark();
        }
        model.addAttribute("completedLabsNum",completedLabs.size());
        model.addAttribute("totalPoints",sum);
        model.addAttribute("countOfAvalibleLabs", labInfoService.getAvalibleLabs(user).size());
        return "profile";
    }

    @PostMapping("/main/profile")
    public String changeUserDetails(@Valid User user) {
        authenticationService.updateUser(user);
        return "redirect:/main/index";
    }
}