package com.example.springdemo.service;

import com.example.springdemo.entity.User;

public interface AuthenticationService {
    String getCurrentUsername();

    User getCurrentUser();
}
