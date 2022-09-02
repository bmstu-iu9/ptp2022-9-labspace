package com.example.springdemo.controllers;

import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.SubmitLabRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.LabInfoService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class ProfilePageController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    SubmitLabRepository submitLabRepository;
    @Autowired
    LabInfoService labInfoService;

    @Autowired
    UserService userService;

    @GetMapping("/main/profile")
    public String getUser( Model model) {
        User user = authenticationService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("groupp", user.getGroupp().getName());
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

    @GetMapping(path = "/main/profile_read_only{user_id}")
    public String getUserReadOnly( Model model, @PathVariable("user_id") Long user_id) {
        User user = userService.getById(user_id);
        model.addAttribute("user", user);
        List<SubmitLab> completedLabs = submitLabRepository.findAllByUserIdAndMarkGreaterThan(user.getId(), 0);
        int sum = 0;
        for (SubmitLab lab: completedLabs){
            sum+= lab.getMark();
        }
        List<SubmitLab> submit_labs = submitLabRepository.findAllByUserIdAndNotChecked(user_id);
        Collections.reverse(submit_labs);
        model.addAttribute("submit_labs", submit_labs);
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("completedLabsNum",completedLabs.size());
        model.addAttribute("totalPoints",sum);
        model.addAttribute("countOfAvalibleLabs", labInfoService.getAvalibleLabs(user).size());

        return "profile_read_only";
    }
}
