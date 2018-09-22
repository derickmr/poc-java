package com.sap.service.impl;

import com.sap.dao.TeamDao;
import com.sap.model.Team;
import com.sap.service.TeamService;
import com.sap.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
