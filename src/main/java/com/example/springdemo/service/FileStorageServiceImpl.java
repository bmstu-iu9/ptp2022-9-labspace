package com.example.springdemo.service;

import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.SubmitLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LabInfoRepository labInfoRepository;

    @Autowired
    private SubmitLabRepository submitLabRepository;

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();

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
    public void storeFile(MultipartFile file, String path, Long labId) {
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
            if(contentType == null ||
                        !(contentType.equals("application/msword") ||
                        contentType.equals("application/pdf") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
                throw new RuntimeException("Allowed filetypes: doc, docx, pdf");
            }
            Files.createDirectories(this.fileStorageLocation.resolve(path));
            Path targetLocation = this.fileStorageLocation.resolve(path).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            SubmitLab submitLab = SubmitLab.builder()
                    .user(authenticationService.getCurrentUser())
                    .source(targetLocation.toString())
                    .labInfo(labInfoRepository.getReferenceById(labId))
                    .sendDate(new Date(System.currentTimeMillis()))
                    .build();

            submitLabRepository.saveAndFlush(submitLab);
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
            }
            else {
                throw new RuntimeException(
                        "Could not read file");

            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }
}
