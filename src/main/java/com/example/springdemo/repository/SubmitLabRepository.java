package com.example.springdemo.repository;

import com.example.springdemo.entity.SubmitLab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmitLabRepository extends JpaRepository<SubmitLab, Long> {
    List<SubmitLab> findByUserEmail(String email);

    List<SubmitLab> findByUserId(Long id);

    Integer getMarkById(Long id);

    Optional<SubmitLab> findByUserIdAndLabInfoId(Long userId, Long labInfoId);

    List<SubmitLab> findAllByUserId(Long userId);

    List<SubmitLab> findByUser_IdAndOnRevisionFalse(Long id);

    List<SubmitLab> findAll();

    List<SubmitLab> findAllByUserIdAndMarkGreaterThan(Long user_id, int mark);
    List<SubmitLab> findAllByUserIdAndMarkGreaterThanAndOnRevisionFalse(Long user_id, int mark);

    @Query("select sl from SubmitLab sl where sl.mark=-1 and sl.user.id=?1")
    List<SubmitLab> findAllByUserIdAndNotChecked(Long user_id);

    @Query("select sl from SubmitLab sl where sl.mark=-1 or sl.onRevision = true")
    List<SubmitLab> findAllNotChecked();

    @Query("select sl from SubmitLab sl where sl.mark <> -1 and sl.onRevision <> true ")
    List<SubmitLab> findAllChecked();

    @Query("select max(sl.id) from SubmitLab sl")
    Optional<Integer> getMaxId();
    List<SubmitLab> findByLabInfoId(Long lab_info_id);

    List<SubmitLab> findAllByLabInfoIdAndMarkGreaterThan(Long lab_info_id, int mark);

    List<SubmitLab> findByLabInfo_IdAndMarkOrLabInfo_IdAndOnRevisionTrue(Long id, int mark, Long id1);

}
