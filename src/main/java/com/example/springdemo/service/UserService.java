package com.example.springdemo.service;

import com.example.springdemo.entity.User;
//import com.example.springdemo.model.UserModel;

import java.util.List;

public interface UserService {
    public List<User> getUsers();
    public User getByEmail(String email);
    public User getById(Long id);
   // public User registerUser(UserModel userModel);
}
