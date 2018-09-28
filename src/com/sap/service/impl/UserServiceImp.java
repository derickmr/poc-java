package com.sap.service.impl;

import com.sap.dao.TeamDao;
import com.sap.dao.UserDao;
import com.sap.model.Team;
import com.sap.model.User;
import com.sap.model.UserType;
import com.sap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Resource
    UserDao userDao;

    @Resource
    TeamDao teamDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);
    }

    @Override
    public void saveAdmin (User user){

        Team team = new Team();

        team.setName("Initial name");

        teamDao.save(team);

        user.setTeam(team);

        user.setUserType(UserType.ADMIN.getUserType());

        save(user);

        team.setTeamOwner(user);

        teamDao.save(team);


    }

    @Override
    public void saveUserAtOwnerTeam(User user, User admin) {

        user.setTeam(admin.getTeam());

        saveUser(user);

    }

    @Override
    public boolean verifyIfActionCanBeAppliedToUser(User user) {

        User currentUser = getCurrentUser();


        if (currentUser.equals(user.getTeam().getTeamOwner()))
            return true;

        if (currentUser.equals(user))
            return true;

        return false;

    }

    @Override
    public User getCurrentUser() {

        String userSso = getPrincipal();

        return getUserBySsoId(userSso);

    }

    @Override
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
    public void deleteUserBySsoId (String ssoId){

        User userToBeDeleted = getUserBySsoId(ssoId);

        Team team = userToBeDeleted.getTeam();

        List<User> users = team.getUsers();

        users.remove(userToBeDeleted);

        team.setUsers(users);

        teamDao.save(team);

        deleteUserByID(userToBeDeleted.getId());

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

    @Override
    public List<User> getTeamMates(User user) {

        List<User> users = user.getTeam().getUsers();
        users.remove(user);

        return users;

    }


    //Method that gets the current logged ssoId
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
