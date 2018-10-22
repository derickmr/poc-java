package com.sap.dao.impl;

import com.sap.dao.UserOnShiftNotificationDao;
import com.sap.model.UserOnShiftNotification;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserOnShiftNotificationDaoImp extends HibernateDaoSupport implements UserOnShiftNotificationDao {

    private final static String FROM_NECESSITYMESSAGE_AS_U = "from com.sap.model.UserOnShiftNotification as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(UserOnShiftNotification message) {
        getHibernateTemplate().saveOrUpdate(message);
    }

    @Override
    public UserOnShiftNotification getNecessityMessageById(Integer id) {
        return (UserOnShiftNotification) getHibernateTemplate().find(FROM_NECESSITYMESSAGE_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public Set<UserOnShiftNotification> getAllNecessityMessages() {
        Set<UserOnShiftNotification> necessityMessages = new HashSet<>((List<UserOnShiftNotification>)getHibernateTemplate().find(FROM_NECESSITYMESSAGE_AS_U));
        return necessityMessages;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteNecessityMessageById(Integer id) {
        UserOnShiftNotification necessityMessage = getNecessityMessageById(id);
        getHibernateTemplate().delete(necessityMessage);
    }
}
