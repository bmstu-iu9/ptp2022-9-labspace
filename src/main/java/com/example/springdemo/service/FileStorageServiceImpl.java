package com.example.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileStorageServiceImpl implements FileStorageService {
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
    @Override
    public String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    @Override
    public void storeFile(MultipartFile file,String path) {
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
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
