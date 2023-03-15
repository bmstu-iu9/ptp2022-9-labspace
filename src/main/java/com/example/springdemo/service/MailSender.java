package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailSender {

    void send(String emailTo, String subject, String message);

    void sendWithAttachments(String emailTo, String subject, String message, String filename, InputStreamSource file)
            throws MessagingException;

    void sendAssessedWork(SubmitLab submitLab, User user);

    void sendMailSubmitLab(LabInfo lab, MultipartFile file);

    void sendActivationCode(User user);
}
