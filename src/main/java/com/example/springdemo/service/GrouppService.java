package com.example.springdemo.service;

import com.example.springdemo.entity.User;

public interface GrouppService {
    void setHeadman(Long grouppId, Long userId);

    void setHeadman(Long grouppId, User user);
}
