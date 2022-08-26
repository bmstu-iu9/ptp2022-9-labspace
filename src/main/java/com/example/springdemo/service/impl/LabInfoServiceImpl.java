package com.example.springdemo.service.impl;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.repository.*;
import com.example.springdemo.service.DeadlineService;
import com.example.springdemo.service.FileStorageService;
import com.example.springdemo.service.LabInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LabInfoServiceImpl implements LabInfoService {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private LabInfoRepository labInfoRepository;
    @Autowired
    private DeadlineService deadlineService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GrouppRepository grouppRepository;


    @Override
    public void uploadLab(LabInfo labInfo, MultipartFile file, HttpServletRequest request) throws ParseException {
        courseRepository.findById(Long.valueOf(request.getParameter("course_id"))).ifPresent(labInfo::setCourse);
        Set<Groupp> groups = Arrays.stream(request.getParameterValues("groupss"))
                .map(id -> grouppRepository.findById(Long.valueOf(id)))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
        labInfo.setUploadDate(new Date(System.currentTimeMillis()));
        labInfo.setGroupps(groups);
        labInfo.setSource("labs/");
        fileStorageService.storeFile(file, labInfo);
        labInfoRepository.save(labInfo);
        deadlineService.saveDeadlines(request, labInfo);
    }

}
