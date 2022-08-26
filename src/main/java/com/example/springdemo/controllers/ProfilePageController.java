package com.example.springdemo.controllers;

import com.example.springdemo.entity.User;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ProfilePageController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/main/profile")
    public String getUser(HttpServletRequest request, Model model){
        User user = authenticationService.getCurrentUser();
        model.addAttribute("user",user);
        return "profile";
    }
    @PostMapping("/main/profile")
    public String changeUserDetails(HttpServletRequest request, Model model, @Valid User user){
        authenticationService.updateUser(user);
        return "redirect:/main/index";
    }
}
