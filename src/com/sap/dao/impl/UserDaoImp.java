package com.sap.dao.impl;

import com.sap.dao.UserDao;
import com.sap.model.User;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserDaoImp extends HibernateDaoSupport implements UserDao {

    private final static String FROM_USER_AS_U = "from com.sap.model.User as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(User user) {
        getHibernateTemplate().saveOrUpdate(user);
    }

    @Override
    public User getUserByID(Integer id) {
        return (User) getHibernateTemplate().find(FROM_USER_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public User getUserBySsoId(String ssoId) {

        List<User> usersWithId =  (List<User>) getHibernateTemplate().find(FROM_USER_AS_U + "where u.ssoId='" + ssoId + "'");
        if(usersWithId.isEmpty())
            return null;
        return usersWithId.get(0);
    }

    @Override
    @Transactional(readOnly = false)
    public User deleteUserByID(Integer id) {

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
        Set<User> users = new HashSet<> ((List<User>) getHibernateTemplate().find(FROM_USER_AS_U));

        return users;
    }

}
