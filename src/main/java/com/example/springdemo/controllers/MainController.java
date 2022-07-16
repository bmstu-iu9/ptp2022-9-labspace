package com.example.springdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/index.html")
    public String index(Model model) {
        Date date = new Date(System.currentTimeMillis());

        //Get all files from directory
        List<File> files = files("C:\\Users\\1\\Desktop\\GitHub"); //Later... Take user's directory
        int n = files.size();
        String[] directory = new String[n];
        for(int i = 0; i < n; i++){
            directory[i] = files.get(i).getName();
        }

        //Try to get IP
        try {
            InetAddress clientIPAddress = InetAddress.getLocalHost();
            model.addAttribute("clientFileSystem", directory);
            model.addAttribute("clientIPAddress", clientIPAddress.getHostAddress());
            model.addAttribute("clientDate", date);
        } catch (Exception ex) {
            model.addAttribute("clientFileSystem", directory);
            model.addAttribute("clientDate", date);
            System.out.println(ex.getMessage());
        }
        return "index";
    }

    @GetMapping("/minor.html")
    public String home2(Model model) {
        Date date = new Date(System.currentTimeMillis());

        //Get all files from directory
        List<File> files = files("C:\\Users\\1\\Desktop\\GitHub"); //Later... Take user's directory
        int n = files.size();
        String[] directory = new String[n];
        for(int i = 0; i < n; i++){
            directory[i] = files.get(i).getName();
        }

        //Try to get IP
        try {
            InetAddress clientIPAddress = InetAddress.getLocalHost();
            model.addAttribute("clientFileSystem", directory);
            model.addAttribute("clientIPAddress", clientIPAddress.getHostAddress());
            model.addAttribute("clientDate", date);
        } catch (Exception ex) {
            model.addAttribute("clientFileSystem", directory);
            model.addAttribute("clientDate", date);
            System.out.println(ex.getMessage());
        }
        return "minor";
    }

    @GetMapping("/profiles.html")
    public String profile(Model model) {
        Date date = new Date(System.currentTimeMillis());

        //Get all files from directory
        List<File> files = files("C:\\Users\\1\\Desktop\\GitHub"); //Later... Take user's directory
        int n = files.size();
        String[] directory = new String[n];
        for(int i = 0; i < n; i++){
            directory[i] = files.get(i).getName();
        }

        //Try to get IP
        try {
            InetAddress clientIPAddress = InetAddress.getLocalHost();
            model.addAttribute("clientFileSystem", directory);
            model.addAttribute("clientIPAddress", clientIPAddress.getHostAddress());
            model.addAttribute("clientDate", date);
        } catch (Exception ex) {
            model.addAttribute("clientFileSystem", directory);
            model.addAttribute("clientDate", date);
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

}
