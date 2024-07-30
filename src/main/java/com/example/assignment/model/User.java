package com.example.assignment.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private  UUID user_id ;
    @Column(name = "manager_id")
    private UUID manager_id;
    @Column(name = "full_name", nullable = false)
    private  String full_name;
    @Column(name = "mob_num", unique = true)
    private  String mob_num;
    @Column(name = "pan_num", unique = true)
    private  String pan_num;
    @Column(name = "created_at")
    @CreationTimestamp
    private  Timestamp created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "is_active", nullable = false)
    private  Boolean is_active = true;


    public  User(){}

    public User(UUID user_id, UUID manager_id, String full_name, Timestamp created_at, LocalDateTime updated_at, Boolean is_active){
        this.user_id = user_id;
        this.manager_id = manager_id;
        this.full_name = full_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_active = is_active;
    }


    public void setManager_id(UUID manager_id){
        this.manager_id = manager_id;
    }
    public UUID getManager_id(){
        return manager_id;
    }

    public void setUser_id(UUID user_id){
        this.user_id = user_id;
    }
    public UUID getUser_id(){
        return user_id;
    }

    public void setFull_name(String full_name){
        this.full_name = full_name;
    }
    public String getFull_name(){
        return  full_name;
    }

    public void  setMob_num(String mob_num){
        this.mob_num = mob_num;
    }
    public String getMob_num(){
        return mob_num;
    }

    public void setPan_num(String pan_num){
        this.pan_num = pan_num;
    }
    public String getPan_num(){
        return pan_num;
    }

    public void setCreated_at(Timestamp created_at){
        this.created_at = created_at;
    }
    public  Timestamp getCreated_at(){
        return  created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at){
        this.updated_at = updated_at;
    }
    public LocalDateTime getUpdated_at(){
        return updated_at;
    }

    public void setIs_active(Boolean is_active){
        this.is_active = is_active;
    }
    public Boolean getIs_active(){
        return is_active;
    }

}
