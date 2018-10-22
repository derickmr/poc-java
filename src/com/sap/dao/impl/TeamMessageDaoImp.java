package com.sap.dao.impl;

import com.sap.dao.TeamMessageDao;
import com.sap.model.TeamMessage;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamMessageDaoImp extends HibernateDaoSupport implements TeamMessageDao {

    private final static String FROM_MESSAGE_AS_U = "from com.sap.model.TeamMessage as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(TeamMessage message) {
        getHibernateTemplate().saveOrUpdate(message);
    }

    @Override
    public TeamMessage getMessageById(Integer id) {
        return (TeamMessage) getHibernateTemplate().find(FROM_MESSAGE_AS_U + "where u.id="+id.toString()).get(0);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMessageById(Integer id) {
        TeamMessage message = getMessageById(id);
        getHibernateTemplate().delete(message);
    }

    @Override
    public Set<TeamMessage> getAllMessages() {
        Set<TeamMessage> messages = new HashSet<>((List<TeamMessage>) getHibernateTemplate().find(FROM_MESSAGE_AS_U));
        return messages;
    }
}