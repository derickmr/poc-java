package com.sap.dao;

import com.sap.model.Team;

public interface TeamDao {

    void save(Team team);

    Team getTeamByID(Integer id);

}
