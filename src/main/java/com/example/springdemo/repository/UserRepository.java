package com.example.springdemo.repository;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    User findByActivationCode(String code);

    User findByResetPasswordToken(String token);

    int countByGroupp(Groupp groupp);

    List<User> findAllByGroupp(Groupp groupp);

}
