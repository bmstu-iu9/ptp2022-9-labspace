package com.example.springdemo.controllers;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.SubmitLabRepository;
import com.example.springdemo.repository.VariantRepository;
import com.example.springdemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
public class SubmitLabController {
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private LabInfoRepository labInfoRepository;
    @Autowired
    private GradesListService gradesListService;
    @Autowired
    private DeadlineRepository deadlineRepository;
    @Autowired
    UserService userService;
    @Autowired
    VariantService variantService;
    @Autowired
    VariantRepository variantRepository;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private SubmitLabRepository submitLabRepository;

    @PostMapping(path = "main/lab_id{lab_info_id}")
    public String uploadFile(
            @RequestParam(name = "filee", required = false) MultipartFile file,
            @PathVariable("lab_info_id") Long labId, Model model) throws IOException {
        String path = labInfoRepository.getReferenceById(labId).getCourse().getId() + "/labid" + labId + "/";
        model.addAttribute("id", labId);
        fileStorageService.storeFile(file, path, labId);
        return "redirect:/";
    }

    @GetMapping(path = "main/lab_id{lab_info_id}")
    public String view(Model model, @PathVariable("lab_info_id") Long lab_id) {
        authenticationService.addNameAndGroupToModel(model);

        Optional<LabInfo> lab_info = labInfoRepository.findById(lab_id);
        User user = authenticationService.getCurrentUser();
        if (!lab_info.isPresent() || !lab_info.get().getGroupps().contains(user.getGroupp())){
            return "redirect:/";
        } else{
            Optional<SubmitLab> submitLab = submitLabRepository.findByUserIdAndLabInfoId(user.getId(), lab_info.get().getId());
            if (submitLab.isPresent() && !submitLab.get().isOnRevision()) {
                return "redirect:/";
            }
        }
        model.addAttribute("lab_info", lab_info.get());
        model.addAttribute("grade", gradesListService.getPointsByStudentAndLab(authenticationService.getCurrentUsername(), lab_id));
        model.addAttribute("deadlines", deadlineRepository.findAllByLabInfoId(lab_id));
        SimpleDateFormat formatdayMonth = new SimpleDateFormat("dd.MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        model.addAttribute("formatdayMonth", formatdayMonth);
        model.addAttribute("formatYear", formatYear);
        model.addAttribute("formatTime", formatTime);



        if (variantRepository.findByLabInfoIdAndStudentId(lab_id, user.getId()).isEmpty()){
            model.addAttribute("variant", "without");
        }
        else{
            int variant = variantService.getVariantByLabInfoIdAndStudentId(lab_id, user.getId());
            model.addAttribute("variant", variant);
        }
        return "templs/templateOfUploadLab";
    }
    @GetMapping(path = "main/lab_id{lab_info_id}/download")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long lab_info_id) {
        Resource file = fileStorageService.loadAsResource(lab_info_id);
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
