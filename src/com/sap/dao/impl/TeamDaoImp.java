package com.sap.dao.impl;

import com.sap.dao.TeamDao;
import com.sap.model.Team;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.transaction.Transactional;

public class TeamDaoImp extends HibernateDaoSupport implements TeamDao {

    private final static String FROM_USER_AS_U = "from com.sap.model.Team as u ";


    @Override
    @Transactional
    public void save(Team team) {
        getHibernateTemplate().save(team);
    }

    @Override
    public Team getTeamByID(Integer id) {
        return (Team) getHibernateTemplate().find(FROM_USER_AS_U + "where u.id=" + id.toString()).get(0);
    }

}
