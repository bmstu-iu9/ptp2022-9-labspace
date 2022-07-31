package com.example.springdemo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

public interface MailSender {

    public void send(String emailTo, String subject, String message);

}
