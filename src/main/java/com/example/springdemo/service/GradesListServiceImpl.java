package com.example.springdemo.service;

import com.example.springdemo.repository.GradesListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GradesListServiceImpl implements GradesListService {
    @Autowired
    private GradesListRepository gradesListRepository;

    @Override
    public int getTotalPointsByStudentId(Long id) {
        Optional<Integer> totalPoints = gradesListRepository.getTotalPointsByStudentId(id);
        return totalPoints.orElse(0);
    }


    @Override
    public int getPointsByStudentAndLab(String email, Long labInfoId) {
        return gradesListRepository.findBySubmitLab_User_EmailAndSubmitLab_LabInfo_Id(email, labInfoId).orElse(0);
    }
}
