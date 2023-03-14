package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.LabInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

@Service
public class MailSenderImpl implements MailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VariantService variantService;
    @Autowired
    private DeadlineService deadlineService;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendWithAttachments(String emailTo, String subject, String message, String filename, InputStreamSource file)
            throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(emailTo);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        mimeMessageHelper.addAttachment(filename, file);

        mailSender.send(mimeMessage);
    }

    // TODO add attachments to email to student

    @Override
    public void sendAssessedWork(SubmitLab submitLab, User user) {
        LabInfo lab = submitLab.getLabInfo();
        String course = lab.getCourse().getName();
        String labName = lab.getName();
        int var = variantService.getVariantByLabInfoIdAndStudentId(lab.getId(), user.getId());
        int mark = submitLab.getMark();
        Date sendDate = submitLab.getSendDate();
        int deadlineNum = deadlineService.getDeadlineNumber(lab, sendDate);

        String subject = course + " " + labName + " Вар." + var + " Оценка " + mark + " принята.";
        String message = "Доброго времени суток, " + user.getFirstName() + "!\n\n" +
                "Лабораторная работа " + labName + " вариант " + var + " по курсу " + course + " загруженная " +
                sendDate + " в дедлайн№" + deadlineNum + " принята с количеством баллов равным: " + mark;

        send(user.getEmail(), subject, message);
    }

}
