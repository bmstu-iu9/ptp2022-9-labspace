package com.example.springdemo.controllers;

import com.example.springdemo.entity.User;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.VariantRepository;
import com.example.springdemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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
    private Calendar calendar;
    @Autowired
    UserService userService;
    @Autowired
    VariantService variantService;
    @Autowired
    VariantRepository variantRepository;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "guest";
    }

    public void addNameAndGroupToModel(Model model) {
        String username;
        username = getCurrentUsername();
        if (!Objects.equals(username, "guest")) {
            User user = userService.getByEmail(username);
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("groupp", user.getGroupp().getName());
        } else {
            model.addAttribute("name", "guest");
            model.addAttribute("groupp", "");
        }
    }

    @PostMapping(path = "main/lab_id{lab_info_id}")
    public String uploadFile(
            @RequestParam(name = "filee", required = false) MultipartFile file, RedirectAttributes redirectAttributes, @PathVariable("lab_info_id") Long labId,
            Model model) {
        String path = labInfoRepository.getReferenceById(labId).getCourse().getName() + "/labid" + labId;
        model.addAttribute("id", labId);
        fileStorageService.storeFile(file, path, labId);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        // return "redirect:/main/lab_id"+labId;
        return "redirect:/";
    }

    @GetMapping(path = "main/lab_id{lab_info_id}")
    public String view(Model model, @PathVariable("lab_info_id") Long lab_id) {
        addNameAndGroupToModel(model);
        model.addAttribute("lab_info", labInfoRepository.getReferenceById(lab_id));
        model.addAttribute("grade", gradesListService.getPointsByStudentAndLab(getCurrentUsername(), lab_id));
        model.addAttribute("deadlines", deadlineRepository.findAllByLabInfoId(lab_id));
        SimpleDateFormat formatdayMonth = new SimpleDateFormat("dd.MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        model.addAttribute("formatdayMonth", formatdayMonth);
        model.addAttribute("formatYear", formatYear);

        String username;
        username = getCurrentUsername();
        User user = userService.getByEmail(username);

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
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
