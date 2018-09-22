package com.sap.service;

import com.sap.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User getUserByID(String id);

    User deleteUserByID(String id);

    void updateUser(User user);

    List<User> getAllUsers();

}
