package com.example.springdemo.service;

import com.example.springdemo.entity.User;
import org.springframework.ui.Model;

public interface AuthenticationService {
    String getCurrentUsername();

    User getCurrentUser();
    void updateUser(User user);
    void addNameAndGroupToModel(Model model);
}
