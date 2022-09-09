
package com.example.springdemo.service;

import com.example.springdemo.entity.Role;
import com.example.springdemo.entity.User;
import com.example.springdemo.exceptions.UserNotFoundException;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new IllegalStateException("This user doesn't exist");
        } else {
            return user.get();
        }
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new IllegalStateException("This student doesn't exist");
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
                    res += str.substring(i, i + 1).toUpperCase();
                } else {
                    res += str.substring(i, i + 1).toLowerCase();
                }
                k += 1;
            } else {
                res += str.substring(i, i + 1);
                k = 0;
            }
        }

        return res;
    }

    @Override
    public void registerUser(User user) {
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setFirstName(capitalize(user.getFirstName()));
        user.setLastName(capitalize(user.getLastName()));
        user.setPhoneNumber("+" + user.getPhoneNumber());
        user.setTgAccount("@" + user.getTgAccount());

        String message = String.format(
                "Hello, %s!\n" +
                        "\n" +
                        "We are glad to welcome you to the LabSpace!\n" +
                        "\n" +
                        "Please, follow the link to verify your profile:\n" +
                        "http://iu9.yss.su/auth/activate/%s",
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
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = getByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException(email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
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

    @Override
    public boolean firstNameContainsIllegalChars(User user) {
        boolean res = false;
        for (int i = 0; i < user.getFirstName().length(); i++) {
            if (!(user.getFirstName().charAt(i) >= 'A' && user.getFirstName().charAt(i) <= 'Z' ||
                    user.getFirstName().charAt(i) >= 'a' && user.getFirstName().charAt(i) <= 'z' ||
                    user.getFirstName().charAt(i) >= 'А' && user.getFirstName().charAt(i) <= 'Я' ||
                    user.getFirstName().charAt(i) >= 'а' && user.getFirstName().charAt(i) <= 'я' ||
                    user.getFirstName().charAt(i) == 'ё' || user.getFirstName().charAt(i) == 'Ё' ||
                    user.getFirstName().charAt(i) == '-')) {
                res = true;
                break;
            }
        }

        return res;
    }

    @Override
    public boolean lastNameContainsIllegalChars(User user) {
        boolean res = false;
        for (int i = 0; i < user.getLastName().length(); i++) {
            if (!(user.getLastName().charAt(i) >= 'A' && user.getLastName().charAt(i) <= 'Z' ||
                    user.getLastName().charAt(i) >= 'a' && user.getLastName().charAt(i) <= 'z' ||
                    user.getLastName().charAt(i) >= 'А' && user.getLastName().charAt(i) <= 'Я' ||
                    user.getLastName().charAt(i) >= 'а' && user.getLastName().charAt(i) <= 'я' ||
                    user.getLastName().charAt(i) == 'ё' || user.getLastName().charAt(i) == 'Ё' ||
                    user.getLastName().charAt(i) == '-')) {
                res = true;
                break;
            }
        }

        return res;
    }
}



