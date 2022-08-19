package com.example.springdemo.service;

import com.example.springdemo.entity.User;
import com.example.springdemo.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getByEmail(String email);

    User getById(Long id);

    void registerUser(User user);

    boolean isAlreadyPresent(User user);

    boolean activateUser(String code);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);
    boolean firstNameContainsIllegalChars(User user);
    boolean lastNameContainsIllegalChars(User user);
}
