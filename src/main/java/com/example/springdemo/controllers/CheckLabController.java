package com.example.springdemo.controllers;

import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.SubmitLab;
import com.example.springdemo.repository.DeadlineRepository;
import com.example.springdemo.repository.LabInfoRepository;
import com.example.springdemo.repository.SubmitLabRepository;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.service.AuthenticationService;
import com.example.springdemo.service.DeadlineService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    @GetMapping("/main/check_lab_id{lab_id}user_id{user_id}")
    public String checkLab(Model model, @PathVariable Long lab_id, @PathVariable Long user_id){
        LabInfo labInfo = labInfoRepository.findById(lab_id).get();
        Optional<SubmitLab> submitLab = submitLabRepository.findByUserIdAndLabInfoId(user_id,lab_id);
        if (!submitLab.isPresent()){
            return "redirect:/main/check_lab_id" + lab_id;
        }
        model.addAttribute("lab_info", labInfo);
        model.addAttribute("user", userRepository.findById(user_id).get());
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
        return "check_lab";
    }

    @PostMapping("/main/check_lab_id{lab_id}user_id{user_id}")
    public String writeMark(@NotNull HttpServletRequest request, @PathVariable Long lab_id, @PathVariable Long user_id){
      Optional <SubmitLab> submitLab1 = submitLabRepository.findByUserIdAndLabInfoId(user_id,lab_id);
      SubmitLab submitLab=submitLab1.get();
       if (Objects.equals(request.getParameter("send-revision"), "1")){
           submitLab.setRevisionComment( request.getParameter("comment"));
           submitLab.setOnRevision(true);
       }
       submitLab.setMark(Integer.parseInt(request.getParameter("final_mark")));
       submitLabRepository.save(submitLab);
       return "redirect:/main/check_lab_id" + lab_id;
    }
}
