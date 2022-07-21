package com.example.springdemo.controllers;

import com.example.springdemo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository){
        return args -> {

        };
    }
}
