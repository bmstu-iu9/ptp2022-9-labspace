package com.example.springdemo.controllers.admin;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
public class GroupListController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GrouppRepository grouppRepository;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(path = "/admin/group{group_id}")
    public String studentsList(Model model, @PathVariable("group_id") Long group_id) {
        String username = authenticationService.getCurrentUsername();
        User user = userService.getByEmail(username);
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        Groupp groupp = grouppRepository.findById(group_id).get();
        List<User> students = userRepository.findAllByGrouppOrderByLastName(groupp);
        model.addAttribute("students", students);
        model.addAttribute("group", groupp);

        return "adminTemp/group";
    }


}
