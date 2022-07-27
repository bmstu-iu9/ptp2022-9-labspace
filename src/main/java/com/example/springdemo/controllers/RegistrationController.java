package com.example.springdemo.controllers;

import com.example.springdemo.entity.Role;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;



    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerUser(@Valid User user){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String regUser(@Valid User user){
        ModelAndView modelAndView= new ModelAndView();
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        modelAndView.addObject("user",new User());
        modelAndView.setViewName("register");
        return "redirect:/login";
    }



}
