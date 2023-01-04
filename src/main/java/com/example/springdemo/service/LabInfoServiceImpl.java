package com.example.springdemo.service;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.*;
import com.example.springdemo.repository.LabInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
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
    @Autowired
    private SubmitLabRepository submitLabRepository;

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    public void uploadLab(LabInfo labInfo, MultipartFile file, HttpServletRequest request) throws ParseException {
        courseRepository.findById(Long.valueOf(request.getParameter("course_id"))).ifPresent(labInfo::setCourse);
        Set<Groupp> groups = Arrays.stream(request.getParameterValues("groupss"))
                .map(id -> grouppRepository.findById(Long.valueOf(id)))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
        labInfo.setUploadDate(new Date(System.currentTimeMillis()));
        labInfo.setGroupps(groups);
        labInfo.setIsVisible(false);
        labInfo.setTeahcer(authenticationService.getCurrentUser());
        fileStorageService.storeFile(file, labInfo);
        deadlineService.saveDeadlines(request, labInfo);
    }

    public Set<LabInfo> getAvalibleLabs(User user) {
        Set<Long> labid = submitLabRepository.findByUser_IdAndOnRevisionFalse(user.getId()).stream()
                .map(a -> a.getLabInfo().getId())
                .collect(Collectors.toSet());
        if (labid.isEmpty()) {
            labid.add(0L);
        }
        return labInfoRepository.findByIsVisibleTrueAndGroupps_IdAndIdNotIn(user.getGroupp().getId(), labid);
    }
}
