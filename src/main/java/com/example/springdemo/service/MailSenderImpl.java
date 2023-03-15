package com.example.springdemo.service;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailSenderImpl implements MailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VariantService variantService;
    @Autowired
    private DeadlineService deadlineService;
    @Autowired
    private AuthenticationService authenticationService;

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

    @Override
    public void sendMailSubmitLab(LabInfo lab, MultipartFile file) {
        User student = authenticationService.getCurrentUser();
        User teacher = lab.getTeahcer();
        String receiver = teacher.getEmail();
        String teacherName;
        if (receiver.equals("root@root")) {
            teacherName = "Данила Павлович";
            receiver = "danila@posevin.com";
        } else if (receiver.equals("av@root")) {
            teacherName = "Александр Владимирович";
            receiver = "avkonovalov@bmstu.ru";
        } else {
            teacherName = teacher.getFirstName() + " " + teacher.getPatronymic();
        }
        String studentName = student.getFirstName() + " " + student.getLastName() + " " + student.getPatronymic();
        int mark = deadlineService.getMarkByDate(lab, new Date());
        String urlToCheckLab = "https://iu9.yss.su/admin/check_lab_id" + lab.getId() + "/user_id" + student.getId();
        String message = "Hello, " + teacherName + ",\n\n" + studentName + " submit laboratory work " +
                lab.getName() + " at " + new Date(System.currentTimeMillis()) + "\n\n" + urlToCheckLab;
        String subject = studentName + " " + student.getGroupp().getName() + " " + lab.getCourse().getName() + " " +
                lab.getName() + " Вариант №" + variantService.getVariantByLabInfoIdAndStudentId(lab.getId(), student.getId()) +
                " Автооценка " + mark;
        try {
            sendWithAttachments(receiver, subject, message,
                    studentName +"_"+ lab.getName() + ".pdf", file);
        } catch (MessagingException e) {
        }
    }

    @Override
    public void sendActivationCode(User user) {
        String message = String.format(
                "Hello, %s!\n" +
                        "\n" +
                        "We are glad to welcome you to the LabSpace!\n" +
                        "\n" +
                        "Please, follow the link to verify your profile:\n" +
                        "http://iu9.yss.su/auth/activate/%s",
                user.getFirstName(),
                user.getActivationCode()
        );
        send(user.getEmail(), "Activation code", message);
    }

}
