package com.sap.dao.impl;

import com.sap.dao.NecessityMessageDao;
import com.sap.model.NecessityMessage;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NecessityMessageDaoImp extends HibernateDaoSupport implements NecessityMessageDao {

    private final static String FROM_NECESSITYMESSAGE_AS_U = "from com.sap.model.NecessityMessage as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(NecessityMessage message) {
        getHibernateTemplate().save(message);
    }

    @Override
    public NecessityMessage getNecessityMessageById(Integer id) {
        return (NecessityMessage) getHibernateTemplate().find(FROM_NECESSITYMESSAGE_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public Set<NecessityMessage> getAllNecessityMessages() {
        Set<NecessityMessage> necessityMessages = new HashSet<>((List<NecessityMessage>)getHibernateTemplate().find(FROM_NECESSITYMESSAGE_AS_U));
        return necessityMessages;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteNecessityMessageById(Integer id) {
        NecessityMessage necessityMessage = getNecessityMessageById(id);
        getHibernateTemplate().delete(necessityMessage);
    }
}
