package com.sap.service;

import com.sap.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void saveAdmin (User user);

    void saveUser (User user);

    User getUserByID(Integer id);

    User getUserBySsoId (String ssoId);

    User deleteUserByID(Integer id);

    void updateUser(User user);

    List<User> getAllUsers();

}
