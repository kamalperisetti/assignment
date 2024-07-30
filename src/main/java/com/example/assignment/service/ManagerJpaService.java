package com.example.assignment.service;

import com.example.assignment.model.Manager;
import com.example.assignment.repository.ManagerJpaRepository;
import com.example.assignment.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ManagerJpaService implements ManagerRepository {
    @Autowired
    private ManagerJpaRepository managerJpaRepository;
    @Override
    public Manager addNewManger(Manager manager) {
//        manager.setManager_id(UUID.randomUUID());
        return managerJpaRepository.save(manager);
    }

    @Override
    public ArrayList<Manager> getAllManagers(Manager manager) {
        if(manager.getManager_id()!=null){
            ArrayList<Manager>  managerById = new ArrayList<>();
            managerById.add(managerJpaRepository.findById(manager.getManager_id()).get());
            return managerById;
        }
        List<Manager> managers = managerJpaRepository.findAll();
        return new ArrayList<>(managers);
    }

}
