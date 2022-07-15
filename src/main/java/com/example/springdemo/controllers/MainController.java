package com.example.springdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.InetAddress;
import java.util.Date;


@Controller
public class MainController {

    @GetMapping("/index.html")
    public String index(Model model) {
        Date date = new Date(System.currentTimeMillis());
        try {
            InetAddress clientIPAddress = InetAddress.getLocalHost();
            model.addAttribute("clientIPAddress", clientIPAddress.getHostAddress());
            model.addAttribute("clientDate", date);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "index";
    }

    @GetMapping("/minor.html")
    public String home2(Model model) {
        Date date = new Date(System.currentTimeMillis());
        try {
            InetAddress clientIPAddress = InetAddress.getLocalHost();
            model.addAttribute("clientIPAddress", clientIPAddress.getHostAddress());
            model.addAttribute("clientDate", date);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "minor";
    }

    @GetMapping("/profiles.html")
    public String profile(Model model) {
        Date date = new Date(System.currentTimeMillis());
        try {
            InetAddress clientIPAddress = InetAddress.getLocalHost();
            model.addAttribute("clientIPAddress", clientIPAddress.getHostAddress());
            model.addAttribute("clientDate", date);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "profiles";
    }

    @GetMapping("/login.html")
    public String login( Model model) {
        return "login";
    }

    @GetMapping("/")
    public String login2( Model model) {
        return "login";
    }

    @GetMapping("/register.html")
    public String register( Model model) {
        return "register";
    }

    public static void main (String[] args){

    }
}
