package com.sap.dao.impl;

import com.sap.dao.TeamDao;
import com.sap.model.Team;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TeamDaoImp extends HibernateDaoSupport implements TeamDao {

    private final static String FROM_USER_AS_U = "from com.sap.model.Team as u ";


    @Override
    @Transactional(readOnly = false)
    public void save(Team team) {
        getHibernateTemplate().saveOrUpdate(team);
    }

    @Override
    public Team getTeamByID(Integer id) {
        return (Team) getHibernateTemplate().find(FROM_USER_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public Team getTeamByName (String name){

        List<Team> usersWithId =  (List<Team>) getHibernateTemplate().find(FROM_USER_AS_U + "where u.name='" + name + "'");
        if(usersWithId.isEmpty())
            return null;
        return usersWithId.get(0);

    }

}
