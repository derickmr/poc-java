package com.sap.service;

import com.sap.model.Team;
import com.sap.model.TeamMessage;
import com.sap.model.UserOnShiftNotification;

import java.util.List;

public interface TeamService {

    void save(Team team);

    Team getTeamByID(Integer id);

    Team getTeamByName (String name);

    List<TeamMessage> getNormalMessages(Team team);

    List<UserOnShiftNotification> getNecessityMessages(Team team);

}
