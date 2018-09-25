package com.sap.service;

import com.sap.model.Team;

public interface TeamService {

    void save(Team team);

    Team getTeamByID(Integer id);

    Team getTeamByName (String name);

}
