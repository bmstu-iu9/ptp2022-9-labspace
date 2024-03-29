package com.example.springdemo.service;

import com.example.springdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "guest";
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByEmail(authentication.getName());
    }

    @Override
    public void updateUser(User user) {
       User oldUser = getCurrentUser();
       oldUser.setFirstName(user.getFirstName());
       oldUser.setLastName(user.getLastName());
       oldUser.setPatronymic(user.getPatronymic());
       oldUser.setTgAccount(user.getTgAccount());
       oldUser.setPhoneNumber(user.getPhoneNumber());
       userService.save(oldUser);
    }
    public void addNameAndGroupToModel(Model model) {
        if (!Objects.equals(getCurrentUsername(), "guest")) {
            User user = getCurrentUser();
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
    }
}
