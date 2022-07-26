package com.example.springdemo.controllers;

import com.example.springdemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository repository){
        return args -> {

        };
    }
}
