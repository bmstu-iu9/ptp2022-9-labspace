package com.example.springdemo.repository;

import com.example.springdemo.entity.GradesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.Optional;

@Repository
public interface GradesListRepository extends JpaRepository<GradesList, Long> {
    @Query("select sum (grades.mark) from GradesList grades where grades.submitLab.user.id = ?1")
    Optional<Integer> getTotalPointsByStudentId(Long id);

    Optional<Integer> findBySubmitLab_User_EmailAndSubmitLab_LabInfo_Id(@Email(message = "Email is not correct!")
                                                                        String submitLab_user_email, Long submitLab_labInfo_id);
}

