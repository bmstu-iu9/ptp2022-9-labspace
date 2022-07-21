package com.example.springdemo.controllers;


import com.example.springdemo.entity.Student;
import com.example.springdemo.service.StudentService;
import com.example.springdemo.service.SubmitLabServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "student")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }
    @GetMapping(path = "id{student_id}")
    public Student getStudent(@PathVariable("student_id") Long id) {
        return studentService.getById(id);
    }


    @PostMapping
    public Student getStudent(@RequestBody String email){
        return studentService.getByEmail(email);
    }
}
