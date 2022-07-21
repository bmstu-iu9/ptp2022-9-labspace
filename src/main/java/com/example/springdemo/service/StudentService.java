package com.example.springdemo.service;

import com.example.springdemo.entity.Student;
import com.example.springdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student getByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (!student.isPresent()) {
            throw new IllegalStateException("this student doesn't exist");
        } else {
            return student.get();
        }
    }

    public Student getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new IllegalStateException("this student doesn't exist");
        } else {
            return student.get();
        }
    }
}
