package com.sap.dao;

import com.sap.model.User;

import java.util.Set;

public interface UserDao {

    void save(User user);

    User getUserByID(String id);

    User deleteUserByID(String id);

    void updateUser(User user);

    Set<User> getAllUsers();


}
