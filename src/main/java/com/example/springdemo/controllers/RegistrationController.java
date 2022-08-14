package com.example.springdemo.controllers;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RegistrationController {
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "guest";
    }
    @Autowired
    UserService userService;
    @Autowired
    GrouppRepository grouppRepository;

    @GetMapping(value = "/register")
    public String registerUser(Model model) {
            if (Objects.equals(getCurrentUsername(), "guest")){
                User user = new User();
                model.addAttribute("user", user);
                model.addAttribute("grouppRepository", grouppRepository);
                return "register";
            }else {
                return "redirect:/index";
            }
    }

    @PostMapping(value = "/register")
    public String regUser(@Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            return "register";
        } else if (userService.isAlreadyPresent(user)) {
            bindingResult.rejectValue("email", "user.email", "An account already exists for this email.");
            model.addAttribute("bindingResult", bindingResult);
            return "register";
        }else if (!user.getPassword().equals(user.getPasswordConfirm())){
            bindingResult.rejectValue("password", "user.getPasswordConfirm", "Passwords are not equal");
            model.addAttribute("bindingResult", bindingResult);
            return "register";
        } else {
            Optional<Groupp> tmpgroup = grouppRepository.findByName(request.getParameter("groupp_name"));
            tmpgroup.ifPresent(user::setGroupp);
            String pass = user.getPassword();
            userService.registerUser(user);
            request.login(user.getEmail(), pass);
            return "redirect:/";
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("success", "User successfully activated!");
        } else {
            model.addAttribute("error", "Activation code is not found!");
        }

       return "activate";
    }
}
