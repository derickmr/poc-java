package com.sap.dao;

import com.sap.model.User;

import java.util.Set;

public interface UserDao {

    void save(User user);

    User getUserByID(Integer id);

    User getUserBySsoId (String ssoId);

    User deleteUserByID(Integer id);

    void updateUser(User user);

    Set<User> getAllUsers();


}
