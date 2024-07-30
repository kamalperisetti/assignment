package com.example.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
import java.util.*;

@Entity
@Table(name="manager")
public class Manager {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID manager_id;
    @Column(name = "full_name")
    private String full_name;
    @OneToMany
    @JoinColumn(name = "manager_id")
    List<User> users = new ArrayList<>();
    public Manager(){}

    public Manager(UUID manager_id, String full_name, List<User> users){
        this.manager_id = manager_id;
        this.full_name = full_name;
        this.users = users;
    }

    public void setManager_id(UUID manager_id){
        this.manager_id = manager_id;
    }
    public UUID getManager_id(){
        return manager_id;
    }

    public void setFull_name(String full_name){
        this.full_name = full_name;
    }
    public String getFull_name(){
        return full_name;
    }
    public List<User> getUsers(){
        return this.users;
    }

    public void setUsers(){
        this.users = users;
    }
}
