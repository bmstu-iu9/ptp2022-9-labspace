package com.example.springdemo.service;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.SubmitLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private MailSenderImpl mailSender;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LabInfoRepository labInfoRepository;

    @Autowired
    private SubmitLabRepository submitLabRepository;
    @Autowired
    Environment env;

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(Environment env) {
        this.fileStorageLocation = Paths.get(System.getProperty("user.home")).resolve(env.getProperty("app.file.upload-dir", "/uploads/files"))
             .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }
    @Override
    public void storeFile(MultipartFile file, String path, Long labId) throws IOException {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            String contentType = file.getContentType();

            // Check if the filetype is not correct
            if (contentType == null ||
                    !(contentType.equals("application/msword") ||
                            contentType.equals("application/pdf") ||
                            contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
                throw new RuntimeException("Allowed filetypes: doc, docx, pdf");
            }
            Files.createDirectories(this.fileStorageLocation.resolve(path));
            Path targetLocation = this.fileStorageLocation.resolve(path).resolve(fileName);
            if (contentType.equals("application/msword")||contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
                fileName = new Date().getTime() + "-file.pdf";
                targetLocation = this.fileStorageLocation.resolve(path).resolve(fileName);
                Path target = this.fileStorageLocation.resolve(path).resolve(fileName);
                try (InputStream docxInputStream = file.getInputStream();
                     OutputStream pdfOutputStream = new FileOutputStream(target.toFile())) {
                    IConverter converter = LocalConverter.builder().build();
                    converter.convert(docxInputStream).as(DocumentType.MS_WORD)
                            .to(pdfOutputStream).as(DocumentType.PDF)
                            .execute();
                    converter.shutDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
            User user = authenticationService.getCurrentUser();
            Optional<SubmitLab> submitLabOpt = submitLabRepository.findByUserIdAndLabInfoId(user.getId(),labId);
            if (submitLabOpt.isPresent()){
                SubmitLab sb = submitLabOpt.get();
                if (sb.isOnRevision()){
                    Files.delete(Paths.get(sb.getSource()));
                    sb.setSource(targetLocation.toString());
                    sb.setOnRevision(false);
                    sb.setRevisionComment(null);
                    sb.setSendDate(new Date((System.currentTimeMillis())));
                    submitLabRepository.saveAndFlush(sb);
                } else {
                    throw new RuntimeException("You have submitted this labwork already!");
                }
            } else {
                SubmitLab submitLab = SubmitLab.builder()
                        .user(authenticationService.getCurrentUser())
                        .source(targetLocation.toString())
                        .labInfo(labInfoRepository.getReferenceById(labId))
                        .sendDate(new Date(System.currentTimeMillis()))
                        .mark(-1)
                        .onRevision(false)
                        .build();
                submitLabRepository.saveAndFlush(submitLab);

                String sender = authenticationService.getCurrentUser().getFirstName() + " " +
                        authenticationService.getCurrentUser().getLastName();
                User teacher = labInfoRepository.getReferenceById(labId).getTeahcer();
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
                String message = "Hello, " + teacherName + ",\n\n" + sender + " submit laboratory work " +
                        labInfoRepository.getReferenceById(labId).getName() + " at " + new Date(System.currentTimeMillis());

                try {
                    mailSender.sendWithAttachments(receiver, "New report from " + sender, message,
                            sender +"_"+ labInfoRepository.getReferenceById(labId).getName() + ".pdf", file);
                } catch (MessagingException e) {
                    throw new RuntimeException("Could not sent mail to teacher.");
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public void storeFile(MultipartFile file, LabInfo labInfo) {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            String contentType = file.getContentType();

            // Check if the filetype is not correct
            if (contentType == null ||
                    !(contentType.equals("application/msword") ||
                            contentType.equals("application/pdf") ||
                            contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
                throw new RuntimeException("Allowed filetypes: doc, docx, pdf");
            }
            Files.createDirectories(this.fileStorageLocation.resolve("labs/"));
            Path targetLocation = this.fileStorageLocation.resolve("labs/" + fileName);
            if (contentType.equals("application/msword")||contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
                fileName =  new Date().getTime() + "-file.pdf";
                targetLocation = this.fileStorageLocation.resolve("labs/" + fileName);
                try (InputStream docxInputStream = file.getInputStream();
                     OutputStream pdfOutputStream = new FileOutputStream(targetLocation.toFile())) {
                    IConverter converter = LocalConverter.builder().build();
                    converter.convert(docxInputStream).as(DocumentType.MS_WORD)
                            .to(pdfOutputStream).as(DocumentType.PDF)
                            .execute();
                    converter.shutDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
            labInfo.setSource(targetLocation.toString());

            labInfoRepository.save(labInfo);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadAsResource(Long labId, Long userId) {
        try {
            String path = submitLabRepository.findByUserIdAndLabInfoId(userId, labId).get().getSource();
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(
                        "Could not read file");

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }

    @Override
    public Resource loadAsResource(Long labId) {
        try {
            String path = labInfoRepository.findById(labId).get().getSource();
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(
                        "Could not read file");

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }
}
