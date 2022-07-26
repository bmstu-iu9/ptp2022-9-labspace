
package com.example.springdemo.service;

import com.example.springdemo.entity.Role;
import com.example.springdemo.entity.User;
//import com.example.springdemo.model.UserModel;
import com.example.springdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

   // @Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers(){
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

   /* @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setGroupp(userModel.getGroupp());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(new Role(1L, "USER"));
        userRepository.save(user);
        return user;
    }

    */
}



