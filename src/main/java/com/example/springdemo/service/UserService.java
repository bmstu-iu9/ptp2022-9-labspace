package com.example.springdemo.service;

import com.example.springdemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getByEmail(String email);

    User getById(Long id);

    void registerUser(User user);

    boolean isAlreadyPresent(User user);

    boolean activateUser(String code);
}
