package com.example.assignment.service;

import com.example.assignment.model.Manager;
import com.example.assignment.model.User;
import com.example.assignment.repository.ManagerJpaRepository;
import com.example.assignment.repository.UserJpaRepository;
import com.example.assignment.repository.UserRepository;
import jakarta.persistence.UniqueConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserJpaService implements UserRepository {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ManagerJpaRepository managerJpaRepository;

    @Override
    public List<User> getAllUsers(User user) {

        String mobileNumber = user.getMob_num();
        UUID userId = user.getUser_id();
        UUID managerId = user.getManager_id();
        System.out.println(managerId);
        List<User> allUsers = userJpaRepository.findAll();
        try{
            if (userId != null) {
                ArrayList<User> userById = new ArrayList<>();
                User findUserById = userJpaRepository.findById(userId).get();
                userById.add(findUserById);
                return userById;
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        if (user.getManager_id() != null) {
//            System.out.println(managerId);
            List<User> managerBasedUsers = allUsers.stream().filter((each) -> {
                if(each.getManager_id() != null){
                    return each.getManager_id().equals(managerId);
                }
                return false;
            }).toList();

            if(managerBasedUsers.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager Based Users Not Found");
            }else{
                return managerBasedUsers;
            }
////            try{
//                Manager manager = managerJpaRepository.findById(managerId).get();
//                return manager.getUsers();
//            } catch (Exception e){
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manager based users not found");
//            }
        }
        if (mobileNumber != null) {

            List<User> mobileBasedUser = allUsers.stream().filter((each) -> each.getMob_num().equals(mobileNumber)).toList();
            if(!mobileBasedUser.isEmpty()){
                return mobileBasedUser;
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Users Not Found with this Mobile Number");
            }

        }
        return userJpaRepository.findAll();
    }
    @Override
    public User addNewUser(User user) {
        String fullName = user.getFull_name();
        String mobileNum = user.getMob_num();
        String panNumber = user.getPan_num();
        UUID managerId = user.getManager_id();
        try {
            if (fullName != null) {
                user.setFull_name(fullName);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please Provide name");
            }
            if (mobileNum != null) {
                if (mobileNum.length() == 10 && !mobileNum.startsWith("0")){
                    if (mobileNum.matches("\\d+")) {
                        user.setMob_num(mobileNum);
                        System.out.println("10 Numbers");
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                    }
                } else if (mobileNum.startsWith("0") && mobileNum.length() == 11) {
                    String extractedNumber = mobileNum.substring(1);
                    if (extractedNumber.matches("\\d+")) {
                        System.out.println("11 Numbers");
                        user.setMob_num(extractedNumber);
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                    }

                } else if (mobileNum.startsWith("+91") && mobileNum.length() == 13) {
                    String extractedNumber = mobileNum.substring(3);
                    if (extractedNumber.matches("\\d+")) {
                        System.out.println("13 Numbers");
                        user.setMob_num(extractedNumber);
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                    }

                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Provide Mobile Number");
            }
            if (panNumber != null) {
                user.setPan_num(panNumber.toUpperCase());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Provide Pan Number");
            }
            if (managerId != null) {
                Optional<Manager> managerOptional = managerJpaRepository.findById(managerId);
                if (managerOptional.isPresent()) {
                    user.setManager_id(managerId);
                }
            }
            return userJpaRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check for any duplication mobile number & pan number must be unique");
        }
    }

    @Override
    public void deleteUser(User user) {

        if (user.getMob_num() != null) {
            List<User> allUsers = userJpaRepository.findAll();
            UUID id = null;
            Optional<User> userOptional = allUsers.stream().filter((each)  ->(each.getMob_num().equals(user.getMob_num()))).findFirst();
            if(userOptional.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found With This Mobile Number");
            }else{
                userJpaRepository.deleteById(userOptional.get().getUser_id());
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Deleted Successfully");
            }
        }
        if (user.getUser_id() != null) {
            if(userJpaRepository.findById(user.getUser_id()).isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found With This UserId");
            } else {
                userJpaRepository.deleteById(user.getUser_id());
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Deleted Successfully");

            }
        }

    }

    @Override
    public List<User> updateUser(List<User> users) {
        ArrayList<User> bulkUsersUpdate = new ArrayList<>();
        for (User user : users) {
            UUID userId = user.getUser_id();
            UUID managerId = user.getManager_id();
            String mobileNum = user.getMob_num();
            String panNumber = user.getPan_num();
            User existingUser = userJpaRepository.findById(userId).get();
            if(managerId != null){
                if(existingUser.getManager_id() != null){
                    try{
                        Manager manager = managerJpaRepository.findById(managerId).get();
                        if (manager.getManager_id() != null) {
                            user.setIs_active(false);
                            existingUser.setManager_id(managerId);
                        }
                    }catch (Exception e){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such Manager Exists");
                    }

                }else{
                    try{
                        Manager manager = managerJpaRepository.findById(managerId).get();
                        if (manager.getManager_id() != null) {
                            existingUser.setManager_id(managerId);
                        }
                    }catch (Exception e){
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Such Manager Exists");
                    }

                }
                if (user.getFull_name() != null) {
                    existingUser.setFull_name(user.getFull_name());
                }
                if (mobileNum != null && !mobileNum.startsWith("0")) {
                    if (mobileNum.length() == 10) {
                        if (mobileNum.matches("\\d+")) {
                            existingUser.setMob_num(mobileNum);
                        } else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Mobile Number");
                        }
                    } else if (mobileNum.startsWith("0") && mobileNum.length() == 11) {
                        String extractedNumber = mobileNum.substring(1);
                        if (extractedNumber.matches("\\d+")) {
                            existingUser.setMob_num(extractedNumber);
                        } else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                        }

                    } else if (mobileNum.startsWith("+91") && mobileNum.length() == 13) {
                        String extractedNumber = mobileNum.substring(3);
                        if (extractedNumber.matches("\\d+")) {
                            existingUser.setMob_num(extractedNumber);
                        } else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                        }

                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please Enter a valid Number");
                    }
                  }

                if (panNumber != null) {
                    existingUser.setPan_num(panNumber.toUpperCase());
                }
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ManagerId should be present otherwise you can't update the user");
            }
            userJpaRepository.save(existingUser);
            existingUser.setUpdated_at(LocalDateTime.now());
            bulkUsersUpdate.add(existingUser);

            }
        return bulkUsersUpdate;
    }
}