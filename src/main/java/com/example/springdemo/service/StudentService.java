package com.example.springdemo.service;

import com.example.springdemo.entity.Role;
import com.example.springdemo.entity.Student;
import com.example.springdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
   // BCryptPasswordEncoder bCryptPasswordEncoder;
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

    public Student findUserById(Long userId) {
        Optional<Student> userFromDb = studentRepository.findById(userId);
        return userFromDb.orElse(new Student());
    }

    public Student getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new IllegalStateException("this student doesn't exist");
        } else {
            return student.get();
        }
    }

    public boolean saveUser(Student user) {
        Optional<Student> userFromDB = studentRepository.findByEmail(user.getEmail());

        if (userFromDB.isPresent()) {
            return false;
        }

        user.setRole(new Role(1L, "ROLE_USER"));
       // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        studentRepository.save(user);
        return true;
    }
}
