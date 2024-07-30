package com.example.assignment.repository;

import com.example.assignment.model.Manager;

import java.util.ArrayList;

public interface ManagerRepository {
    Manager addNewManger(Manager manager);
    ArrayList<Manager> getAllManagers(Manager manager);

}
