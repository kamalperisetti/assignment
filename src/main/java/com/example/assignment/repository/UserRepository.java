package com.example.assignment.repository;

import com.example.assignment.model.User;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.UUID;

public interface UserRepository {
    List<User> getAllUsers(User user);
    User addNewUser(User user);
    void deleteUser(User user);
    List<User> updateUser(List<User> users);
}

