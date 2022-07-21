package com.example.springdemo.service;

import com.example.springdemo.entity.Student;
import com.example.springdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
private final StudentRepository studentRepository;
@Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
return studentRepository.findAll();
    }
    public Student getStudent(String email) {
        //  System.out.println(email+ "lox");
        Optional<Student> student = studentRepository.findStudentByEmail(email);
        if (student.isEmpty()) {
            throw new IllegalStateException("this student doesn`t exist");
        }else return student.get();
    }
    public Student getById(long id){
    Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new IllegalStateException("this student doesn`t exist");
        }else return student.get();
    }
}
