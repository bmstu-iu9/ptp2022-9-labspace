package com.example.springdemo.repository;

import com.example.springdemo.entity.Groupp;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrouppRepository extends JpaRepository<Groupp, Long> {
    Optional<Groupp> findByName(String name);

    @Query("select sl from Groupp sl where sl.id <> 12")
    List<Groupp> findAllExpTeacher();

    @Override
    @NotNull
    Optional<Groupp> findById(@NotNull Long aLong);
}
