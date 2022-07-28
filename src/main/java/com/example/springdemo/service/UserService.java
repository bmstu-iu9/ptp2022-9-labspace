package com.example.springdemo.service;

import com.example.springdemo.entity.User;
//import com.example.springdemo.model.UserModel;

import java.util.List;

public interface UserService {
    List<User> getUsers();
     User getByEmail(String email);
     User getById(Long id);
     void registerUser(User user);
     boolean isAlreadyPresent(User user);
   // public User registerUser(UserModel userModel);
}
