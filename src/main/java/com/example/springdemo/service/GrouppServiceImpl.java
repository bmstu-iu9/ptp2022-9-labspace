package com.example.springdemo.service;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.GrouppRepository;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrouppServiceImpl implements GrouppService {
    @Autowired
    private GrouppRepository grouppRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void setHeadman(Long grouppId, Long userId) {
        Groupp groupp = grouppRepository.getReferenceById(grouppId);
        if (groupp.getGrouppLeader() != null) throw new RuntimeException("This group already have leader");

        groupp.setGrouppLeader(userRepository.getReferenceById(userId));

        grouppRepository.save(groupp);
    }

    @Override
    public void setHeadman(Long grouppId, User user) {
        Groupp groupp = grouppRepository.getReferenceById(grouppId);

        if (groupp.getGrouppLeader() != null) throw new RuntimeException("This group already have leader");

        groupp.setGrouppLeader(user);

        grouppRepository.save(groupp);
    }
}
