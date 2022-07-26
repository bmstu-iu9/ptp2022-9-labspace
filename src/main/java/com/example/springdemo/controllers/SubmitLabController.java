package com.example.springdemo.controllers;

import com.example.springdemo.service.UserService;
import com.example.springdemo.service.SubmitLabServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "student")
public class SubmitLabController {
    private final SubmitLabServiceImpl submitLabService;

    @Autowired
    public SubmitLabController(SubmitLabServiceImpl submitLabService) {
        this.submitLabService = submitLabService;
    }
    @GetMapping(path = "id{student_id}/labs")
    public int GetCompleteLabsbyId(@PathVariable("student_id") Long id){
      return  submitLabService.getCompleteLabsByUserId(id).size();
    }
}
