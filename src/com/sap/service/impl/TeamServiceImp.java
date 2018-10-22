package com.sap.service.impl;

import com.sap.dao.TeamDao;
import com.sap.model.Team;
import com.sap.model.TeamMessage;
import com.sap.model.UserOnShiftNotification;
import com.sap.service.TeamService;
import com.sap.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImp implements TeamService {

    @Resource
    TeamDao teamDao;

    @Override
    public void save(Team team) {
        teamDao.save(team);
    }

    @Override
    public Team getTeamByID(Integer id) {
        return teamDao.getTeamByID(id);
    }

    @Override
    public Team getTeamByName (String name){
        return teamDao.getTeamByName(name);
    }

    @Override
    public List<TeamMessage> getNormalMessages(Team team) {
        List<TeamMessage> messages = new ArrayList<>(team.getMessages());
        return messages;
    }

    @Override
    public List<UserOnShiftNotification> getNecessityMessages(Team team) {
        List<UserOnShiftNotification> messages = new ArrayList<>(team.getShiftMessages());
        return messages;
    }
}
