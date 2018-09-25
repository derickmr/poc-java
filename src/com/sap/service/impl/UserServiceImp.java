package com.sap.service.impl;

import com.sap.dao.UserDao;
import com.sap.model.User;
import com.sap.model.UserType;
import com.sap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);
    }

    public void saveAdmin (User user){

        user.setUserType(UserType.ADMIN.getUserType());
        save(user);

    }

    public void saveUser (User user){

        user.setUserType(UserType.USER.getUserType());
        save(user);

    }

    @Override
    public User getUserByID(Integer id) {
        return userDao.getUserByID(id);
    }

    @Override
    public User getUserBySsoId(String ssoId) {
        return userDao.getUserBySsoId(ssoId);
    }

    @Override
    public User deleteUserByID(Integer id) {
        return userDao.deleteUserByID(id);
    }

    @Override
    public void updateUser(User user) {

        userDao.updateUser(user);

    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>(userDao.getAllUsers());

        return users;
    }
}
