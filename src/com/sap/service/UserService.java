package com.sap.service;

import com.sap.model.Team;
import com.sap.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void saveAdmin (User user);

    void saveUser (User user);

    User getUserByID(Integer id);

    User getUserBySsoId (String ssoId);

    User deleteUserByID(Integer id);

    void deleteUserBySsoId (String ssoId);

    void updateUser(User user);

    void saveUserAtOwnerTeam (User user, User admin);

    List<User> getAllUsers();

    List<User> getTeamMates (User user);

    User getCurrentUser ();

    boolean verifyIfActionCanBeAppliedToUser (User user);

    boolean isTeamOwner (User user);

}
