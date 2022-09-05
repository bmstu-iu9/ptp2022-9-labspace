package com.example.springdemo.controllers;

import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileDownloadController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("admin/check_lab_id{labId}/download/user_id{userId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long labId, @PathVariable Long userId) {

        Resource file = fileStorageService.loadAsResource(labId, userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("content-disposition", "inline;filename=" + file.getFilename());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String fileName = file.getFilename();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0
                && (fileName.substring(fileName.lastIndexOf(".") + 1).equals("pdf"))){
            return new ResponseEntity<>(file,headers, HttpStatus.OK);
        }else {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }
    }
}
