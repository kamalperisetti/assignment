package com.example.assignment.controller;

import com.example.assignment.model.User;
import com.example.assignment.service.UserJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class UserJpaController {
    @Autowired
    public UserJpaService userJpaService;

    @PostMapping("/get_users")
    public List<User> getAllUsers(@RequestBody  User user){
        return userJpaService.getAllUsers(user);
    }
    @PostMapping("/create_user")
    public User addNewUser(@RequestBody User user){
        return userJpaService.addNewUser(user);
    }

    @PostMapping("/delete_user")
    void deleteUser(@RequestBody User user){
        userJpaService.deleteUser(user);
    }

    @PostMapping("/update_user")
    public List<User> updateUser(@RequestBody List<User> users){
        return userJpaService.updateUser(users);
    }

}
