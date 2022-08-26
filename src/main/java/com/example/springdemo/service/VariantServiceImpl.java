package com.example.springdemo.service;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import com.example.springdemo.entity.User;
import com.example.springdemo.entity.Variant;
import com.example.springdemo.repository.UserRepository;
import com.example.springdemo.repository.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

@Service
public class VariantServiceImpl implements VariantService {
    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void randomizeVariants(int n, LabInfo labInfo) {
        Set<Groupp> groupps = labInfo.getGroupps();

        int count = 0;
        for (Groupp groupp : groupps) {
            count += userRepository.countByGroupp(groupp);
        }

        ArrayList<Integer> tmpVariants = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            tmpVariants.add(i);
        }

        ArrayList<Integer> variants = new ArrayList<>();
        while (variants.size() < count) {
            Collections.shuffle(tmpVariants);
            variants.addAll(tmpVariants);
        }

        int k = 0;
        for (Groupp groupp : groupps) {
            for (User user : userRepository.findAllByGroupp(groupp)) {
                Variant variant = Variant.builder()
                        .variant(variants.get(k))
                        .student(user)
                        .labInfo(labInfo)
                        .build();

                variantRepository.saveAndFlush(variant);
                k++;
            }
        }
    }

    @Override
    public int getVariantByLabInfoIdAndStudentId(Long lab_info_id, Long student_id) {
        return variantRepository.findByLabInfoIdAndStudentId(lab_info_id, student_id).get().getVariant();
    }
}
