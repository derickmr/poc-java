package com.sap.dao.impl;

import com.sap.dao.UserDayRelationDao;
import com.sap.model.User;
import com.sap.model.UserDayRelation;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDayRelationDaoImp extends HibernateDaoSupport implements UserDayRelationDao {

    private final static String FROM_USER_DAY_RELATION_AS_U = "from com.sap.model.UserDayRelation as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(UserDayRelation userDayRelation) {
        getHibernateTemplate().saveOrUpdate(userDayRelation);
    }

    @Override
    public UserDayRelation getWorkDayByUser(User user) {
        Integer userId = user.getId();

         return (UserDayRelation) getHibernateTemplate().find(FROM_USER_DAY_RELATION_AS_U + "where u.user.id=" + userId.toString()).get(0);
    }

    @Override
    public UserDayRelation getWorkDayById(Integer id) {
        return (UserDayRelation) getHibernateTemplate().find(FROM_USER_DAY_RELATION_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public Set<UserDayRelation> getAllWorkDays() {

        Set<UserDayRelation> userDayRelationSet = new HashSet<>((List<UserDayRelation>) getHibernateTemplate().find(FROM_USER_DAY_RELATION_AS_U));

        return userDayRelationSet;

    }
	
	@Override
    @Transactional(readOnly = false)
    public void deleteUserDayRelationById(Integer id) {
        UserDayRelation userDayRelation = getWorkDayById(id);
        getHibernateTemplate().delete(userDayRelation);
    }
}
