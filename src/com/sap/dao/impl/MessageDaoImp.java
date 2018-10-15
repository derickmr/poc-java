package com.sap.dao.impl;

import com.sap.dao.MessageDao;
import com.sap.model.Message;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageDaoImp extends HibernateDaoSupport implements MessageDao {

    private final static String FROM_MESSAGE_AS_U = "from com.sap.model.Message as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(Message message) {
        getHibernateTemplate().saveOrUpdate(message);
    }

    @Override
    public Message getMessageById(Integer id) {
        return (Message) getHibernateTemplate().find(FROM_MESSAGE_AS_U + "where u.id="+id.toString()).get(0);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMessageById(Integer id) {
        Message message = getMessageById(id);
        getHibernateTemplate().delete(message);
    }

    @Override
    public Set<Message> getAllMessages() {
        Set<Message> messages = new HashSet<>((List<Message>) getHibernateTemplate().find(FROM_MESSAGE_AS_U));
        return messages;
    }
}