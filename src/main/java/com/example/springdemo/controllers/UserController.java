package com.example.springdemo.controllers;


import com.example.springdemo.entity.User;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(path = "student")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public List<User> getStudents(){
        return userService.getUsers();
    }
    @GetMapping(path = "id{student_id}")
    public User getStudent(@PathVariable("student_id") Long id) {
        return userService.getById(id);
    }


    @PostMapping
    public User getStudent(@RequestBody String email){
        return userService.getByEmail(email);
    }
}
