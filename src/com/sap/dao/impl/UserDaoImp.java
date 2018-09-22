package com.sap.dao.impl;

import com.sap.dao.UserDao;
import com.sap.model.User;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.HashSet;
import java.util.Set;

public class UserDaoImp extends HibernateDaoSupport implements UserDao {

    private final static String FROM_USER_AS_U = "from com.sap.model.User as u ";


    @Override
    public void save(User user) {
        getHibernateTemplate().save(user);
    }

    @Override
    public User getUserByID(String id) {
        return (User) getHibernateTemplate().find(FROM_USER_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public User deleteUserByID(String id) {

        User user = getUserByID(id);

        getHibernateTemplate().delete(user);

        return user;

    }

    @Override
    public void updateUser(User user) {

        getHibernateTemplate().update(user);

    }

    @Override
    public Set<User> getAllUsers() {
        return (HashSet<User>) getHibernateTemplate().find(FROM_USER_AS_U);

    }

}
