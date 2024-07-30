package com.example.assignment.controller;

import com.example.assignment.model.Manager;
import com.example.assignment.service.ManagerJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ManagerJpaController {
    @Autowired
    public ManagerJpaService managerJpaService;

    @PostMapping("/create_manager")
    public Manager addNewManager(@RequestBody Manager manager){
        return managerJpaService.addNewManger(manager);
    }

    @PostMapping("/get_manager")
    public ArrayList<Manager> getAllManagers(@RequestBody Manager manager){
        return managerJpaService.getAllManagers(manager);
    }
}
