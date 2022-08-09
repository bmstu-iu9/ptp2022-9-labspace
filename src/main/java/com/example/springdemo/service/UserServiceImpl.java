
package com.example.springdemo.service;

import com.example.springdemo.entity.Role;
import com.example.springdemo.entity.User;
//import com.example.springdemo.model.UserModel;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private MailSender mailSender;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new IllegalStateException("this user doesn't exist");
        } else {
            return user.get();
        }
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalStateException("this student doesn't exist");
        } else {
            return user.get();
        }
    }

    public void registerUser(User user) {
        user.setActive(false);
        user.setGroupp(user.getGroupp());
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());

        String message = String.format(
                "Hello, %s!\n" +
                        "\n" +
                        "We are glad to welcome you to the LabSpace!\n" +
                        "\n" +
                        "Please, follow the link to verify your profile:\n" +
                        "http://localhost:8080/activate/%s", // iu9.yss.su
                user.getFirstName(),
                user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Activation code", message);

        userRepository.save(user);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = getByEmail(email);
        if (user == null) {
            return;
        }

        user.setResetPasswordToken(token);
        userRepository.save(user);

    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public boolean isAlreadyPresent(User user) {
        return userRepository.existsUserByEmail(user.getEmail());
    }
}



