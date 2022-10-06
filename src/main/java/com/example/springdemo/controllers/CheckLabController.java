package com.example.springdemo.controllers;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.entity.User;
import com.example.springdemo.repository.*;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.DeadlineService;
import com.example.springdemo.service.UserService;
import com.example.springdemo.service.VariantService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CheckLabController {
    @Autowired
    LabInfoRepository labInfoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubmitLabRepository submitLabRepository;
    @Autowired
    DeadlineRepository deadlineRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;
    @Autowired
    VariantRepository variantRepository;
    @Autowired
    VariantService variantService;

    @GetMapping("admin/check_lab_id{lab_id}/user_id{user_id}")
    public String checkLab(Model model, @PathVariable Long lab_id, @PathVariable Long user_id){
        LabInfo labInfo = labInfoRepository.findById(lab_id).get();
        Optional<SubmitLab> submitLab = submitLabRepository.findByUserIdAndLabInfoId(user_id,lab_id);
        if (!submitLab.isPresent()){
            return "redirect:admin/check_lab_id" + lab_id;
        }
        User user = userRepository.findById(user_id).get();
        model.addAttribute("lab_info", labInfo);
        model.addAttribute("user", user);
        model.addAttribute("submit_lab", submitLab.get());
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        model.addAttribute("format", format);
        SimpleDateFormat formatdayMonth = new SimpleDateFormat("dd.MM");
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        model.addAttribute("formatdayMonth", formatdayMonth);
        model.addAttribute("formatYear", formatYear);
        model.addAttribute("formatTime", formatTime);
        authenticationService.addNameAndGroupToModel(model);
        model.addAttribute("deadlines", deadlineRepository.findAllByLabInfoId(lab_id));
        if (variantRepository.findByLabInfoIdAndStudentId(lab_id, user.getId()).isEmpty()){
            model.addAttribute("variant", "without");
        }
        else{
            int variant = variantService.getVariantByLabInfoIdAndStudentId(lab_id, user.getId());
            model.addAttribute("variant", variant);
        }
        return "check_lab";
    }

    @PostMapping("/admin/check_lab_id" + "{lab_id}" + "/user_id" + "{user_id}")
    public String writeMark(@NotNull HttpServletRequest request, @PathVariable Long lab_id, @PathVariable Long user_id){
      Optional <SubmitLab> submitLab1 = submitLabRepository.findByUserIdAndLabInfoId(user_id,lab_id);
      SubmitLab submitLab=submitLab1.get();
       if (Objects.equals(request.getParameter("send-revision"), "1")){
           submitLab.setRevisionComment( request.getParameter("comment"));
           submitLab.setOnRevision(true);
       }
      try {
          submitLab.setMark(Integer.parseInt(request.getParameter("final_mark")));
      } catch (NumberFormatException ex) {
          submitLab.setMark(0);
      }
       submitLabRepository.save(submitLab);
       return "redirect:/admin/check_lab_id" + lab_id;
    }

    @GetMapping("/admin/check_lab_id{lab_id}")
    public String allReportsLabPage(Model model, @PathVariable Long lab_id){
        String username = authenticationService.getCurrentUsername();
        User user = userService.getByEmail(username);
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("groupp", user.getGroupp().getName());
        List<SubmitLab> submit_labs = submitLabRepository.findAllByLabInfoIdAndMark(lab_id, -1);
        Optional<LabInfo> lab_info = labInfoRepository.findById(lab_id);
        model.addAttribute("lab_info", lab_info.get());
        Collections.reverse(submit_labs);
        model.addAttribute("submit_labs", submit_labs);
        List<SubmitLab> submit_labs_graded = submitLabRepository.findAllByLabInfoIdAndMarkGreaterThan(lab_id, -1);
        Collections.reverse(submit_labs_graded);
        model.addAttribute("submit_labs_graded", submit_labs_graded);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        model.addAttribute("format", format);
        return "adminTemp/reports_page";
    }
}
