package com.example.springdemo.controllers;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.MailSender;
import com.example.springdemo.service.UserService;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RegistrationController {
    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    GrouppRepository grouppRepository;

    @Autowired
    private MailSender mailSender;

    @GetMapping(value = "/auth/register")
    public String registerUser(Model model) {
        if (Objects.equals(authenticationService.getCurrentUsername(), "guest")) {
            User user = new User();
            model.addAttribute("user", user);
            model.addAttribute("groupps", grouppRepository.findAll());
            return "register";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/auth/register")
    public String regUser(@Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes,
                          HttpServletRequest request,
                          HttpServletResponse response) throws ServletException {
        List<Groupp> grouppList = grouppRepository.findAll();
        model.addAttribute("groupps", grouppList);
        if (!user.getTgAccount().matches("[a-zA-Z]\\w{4,}")){
            bindingResult.rejectValue("tgAccount","user.tgAccount","Telegram username is incorrect");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        }


        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse("+" + user.getPhoneNumber(), Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
            if (!phoneNumberUtil.isValidNumber(phoneNumber))
                throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "Not a number");
        } catch (NumberParseException e) {
            bindingResult.rejectValue("phoneNumber", "user.phoneNumber", "Phone number is not correct");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        }
        if (userService.isAlreadyPresent(user)) {
            bindingResult.rejectValue("email", "user.email", "An account already exists for this email.");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        } else if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.rejectValue("password", "user.getPasswordConfirm", "Passwords are not equal");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        } else if (userService.firstNameContainsIllegalChars(user)) {
            bindingResult.rejectValue("firstName", "user.containsIllegalChar", "Имя может содержать только буквы и символ \"-\"");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        } else if (userService.lastNameContainsIllegalChars(user)) {
            bindingResult.rejectValue("lastName", "user.containsIllegalChar", "Фамилия может содержать только буквы и символ \"-\"");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("groupps", grouppList);
            return "register";
        } else {
            grouppRepository.findById(Long.valueOf(request.getParameter("groupp_name"))).ifPresent(user::setGroupp);
            user.setPhoneNumber("+" + user.getPhoneNumber());
            user.setTgAccount("@" + user.getTgAccount());
            userService.registerUser(user);
            mailSender.sendActivationCode(user);
            redirectAttributes.addAttribute("activationEmail", user.getEmail());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/auth/login")
    public String login(@ModelAttribute("activationEmail") String email) {
        if (Objects.equals(authenticationService.getCurrentUsername(), "guest")) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @Autowired
    UserRepository userRepository;


    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }


    @GetMapping("/auth/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        if (!Objects.equals(authenticationService.getCurrentUsername(), "guest")) {
            return "redirect:/";
        }
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("success", "User successfully activated!");
        } else {
            model.addAttribute("error", "Activation code is not found!");
        }

        return "activate";
    }
}
