package com.example.springdemo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

public interface MailSender {

    public void send(String emailTo, String subject, String message);

    public void sendWithAttachments(String emailTo, String subject, String message, String filename, MultipartFile file)
            throws MessagingException;

}
