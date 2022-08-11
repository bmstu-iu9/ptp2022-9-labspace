
package com.example.springdemo.service;

import com.example.springdemo.entity.Groupp;
import com.example.springdemo.entity.Role;
import com.example.springdemo.entity.User;
//import com.example.springdemo.model.UserModel;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private String capitalize(String str) {
        int k = 0;
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z' ||
                    str.charAt(i) >= 'a' && str.charAt(i) <= 'z' ||
                    str.charAt(i) >= 'А' && str.charAt(i) <= 'Я' ||
                    str.charAt(i) >= 'а' && str.charAt(i) <= 'я' ||
                    str.charAt(i) == 'ё' || str.charAt(i) == 'Ё') {

                if (k == 0) {
                    res += str.substring(i, i+1).toUpperCase();
                } else {
                    res += str.substring(i, i+1).toLowerCase();
                }
                k += 1;
            } else {
                res += str.substring(i, i+1);
                k = 0;
            }
        }

        return res;
    }

    @Override
    public void registerUser(User user) {
        user.setActive(false);
        user.setGroupp(user.getGroupp());
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setFirstName(capitalize(user.getFirstName()));
        user.setLastName(capitalize(user.getLastName()));

        String message = String.format(
                "Hello, %s!\n" +
                        "\n" +
                        "We are glad to welcome you to the LabSpace!\n" +
                        "\n" +
                        "Please, follow the link to verify your profile:\n" +
                        "http://iu9.yss.su/activate/%s",
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
    public boolean isAlreadyPresent(User user) {
        return userRepository.existsUserByEmail(user.getEmail());
    }
}



