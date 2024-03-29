package com.example.springdemo.controllers;

import com.example.springdemo.entity.User;
import com.example.springdemo.exceptions.UserNotFoundException;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.MailSender;
import com.example.springdemo.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Objects;

@Controller
public class ForgotPasswordController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/forgot_password")
    public String showForgotPasswordForm() {
        if (!Objects.equals(authenticationService.getCurrentUsername(), "guest")) {
            return "redirect:/";
        }
        return "forgot_password";
    }

    @PostMapping("/auth/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) throws UserNotFoundException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://iu9.yss.su/auth/reset_password?token=" + token;

            String message = String.format(
                    "Hello!\n" +
                            "\n" +
                            "You have requested to reset your password.\n" +
                            "\n" +
                            "Click the link below to change your password:\n" +
                            resetPasswordLink
            );

            mailSender.send(email, "Reset password link", message);
            model.addAttribute("message", "A reset password link have sent to your email.");
        } catch (UserNotFoundException | IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "forgot_password";
    }

    @GetMapping("/auth/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        if (!Objects.equals(authenticationService.getCurrentUsername(), "guest")) {
            return "redirect:/";
        }
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("error", "Invalid Token");
            return "success_reset";
        }

        return "reset_password";
    }

    @PostMapping("/auth/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
        String token = httpServletRequestWrapper.getRequest().getParameter("token");
        String password = request.getParameter("password");
        User user = userService.getByResetPasswordToken(token);
        System.out.println("user: " + user);

        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("error", "Invalid Token");
            return "success_reset";
        } else {
            userService.updatePassword(user, password);
            user.setResetPasswordToken(null);
            model.addAttribute("success", "You have successfully changed your password.");
        }

        return "success_reset";
    }
}
