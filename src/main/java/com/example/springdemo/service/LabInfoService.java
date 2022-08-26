package com.example.springdemo.service;

import com.example.springdemo.entity.Deadline;
import com.example.springdemo.entity.LabInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface LabInfoService {
    void uploadLab(LabInfo labInfo, MultipartFile file,HttpServletRequest request) throws ParseException;

}
