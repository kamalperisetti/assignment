package com.example.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.assignment.model.User;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
}
