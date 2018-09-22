package com.sap.service.impl;

import com.sap.dao.UserDao;
import com.sap.model.User;
import com.sap.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User getUserByID(String id) {
        return userDao.getUserByID(id);
    }

    @Override
    public User deleteUserByID(String id) {
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
