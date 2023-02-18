package com.example.springdemo.repository;

import com.example.springdemo.entity.LabInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LabInfoRepository extends JpaRepository<LabInfo, Long> {
    List<LabInfo> findByIsVisible(Boolean isVisible);

    Set<LabInfo> findByIsVisibleTrueAndGroupps_IdAndIdNotIn(Long id, Set<Long> ids);

    Set<LabInfo> findByGroupps_Id(Long id);

    List<LabInfo> findAllOrderedById(Long id);

    @NotNull LabInfo getById (Long id);


}
