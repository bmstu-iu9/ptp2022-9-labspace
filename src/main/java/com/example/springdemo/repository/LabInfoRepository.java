package com.example.springdemo.repository;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.LabInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface LabInfoRepository extends JpaRepository<LabInfo, Long> {
    List<LabInfo> findByIsVisible(Boolean isVisible);
    Set<LabInfo> findByIsVisibleTrueAndGroupps_IdAndIdNotIn(Long id, Collection<Long> ids);


}
