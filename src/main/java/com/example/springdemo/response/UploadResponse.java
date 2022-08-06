package com.example.springdemo.response;

public class UploadResponse {
    private final String fileName;

    public UploadResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
