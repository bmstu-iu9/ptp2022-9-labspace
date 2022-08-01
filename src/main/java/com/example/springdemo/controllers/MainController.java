package com.example.springdemo.controllers;

import com.example.springdemo.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RequestService requestService;

    @GetMapping("/index.html")
    public String index(HttpServletRequest request, Model model) {
        //get Date
        Date date = new Date(System.currentTimeMillis());
        //get IP
        String clientIp = requestService.getClientIp(request);

        //Get all files from directory
        List<File> files = files("C:\\Users\\1\\Desktop\\GitHub"); //Later... Take user's directory
        int n = files.size();
        String[] directory = new String[n];
        for (int i = 0; i < n; i++) {
            directory[i] = files.get(i).getName();
        }

        model.addAttribute("clientFileSystem", directory);
        model.addAttribute("clientDate", date);
        model.addAttribute("clientIPAddress", clientIp);

        return "index";
    }

    @GetMapping("/minor.html")
    public String home2(HttpServletRequest request, Model model) {
        //get Date
        Date date = new Date(System.currentTimeMillis());
        //get IP
        String clientIp = requestService.getClientIp(request);

        //Get all files from directory
        List<File> files = files("C:\\Users\\1\\Desktop\\GitHub"); //Later... Take user's directory
        int n = files.size();
        String[] directory = new String[n];
        for (int i = 0; i < n; i++) {
            directory[i] = files.get(i).getName();
        }

        model.addAttribute("clientFileSystem", directory);
        model.addAttribute("clientDate", date);
        model.addAttribute("clientIPAddress", clientIp);
        return "minor";
    }

    @GetMapping("/profiles.html")
    public String profile(Model model) {
        Date date = new Date(System.currentTimeMillis());

        //Get all files from directory
        List<File> files = files("C:\\Users\\1\\Desktop\\GitHub"); //Later... Take user's directory
        int n = files.size();
        String[] directory = new String[n];
        for (int i = 0; i < n; i++) {
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

}
