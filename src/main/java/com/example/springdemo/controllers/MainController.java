package com.example.springdemo.controllers;

import com.example.springdemo.entity.User;
import com.example.springdemo.service.RequestService;
import com.example.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class MainController {
    //function of getting directory
    public static List<File> files(String dirname) {
        if (dirname == null) {
            return Collections.emptyList();
        }

        File dir = new File(dirname);
        if (!dir.exists()) {
            return Collections.emptyList();
        }

        if (!dir.isDirectory()) {
            return Collections.singletonList(dir);
        }

        return Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .collect(Collectors.toList());
    }
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "guest";
    }

    public void addNameAndGroupToModel(Model model){
        String username;
        username = getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp",user.getGroupp().getName());
        }
        else{
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
    }

    @Autowired
    private RequestService requestService;

    @Autowired
    UserService userService;
    @GetMapping("/")
    public String view(){
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        addNameAndGroupToModel(model);
        return "index";
    }


    @GetMapping("/minor")
    public String home2(HttpServletRequest request, Model model) {
        addNameAndGroupToModel(model);
        return "minor";
    }
    @GetMapping("/login")
        public String login(HttpServletRequest request){
        if (getCurrentUsername()== "guest"){
            return "login";
        }else {
            return "redirect:/";
        }
}
    @GetMapping("/lab")
    public String lab(HttpServletRequest request, Model model){
        return "lab";
    }
}
