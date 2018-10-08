package com.sap.dao.impl;

import com.sap.dao.WorkDayDao;
import com.sap.model.User;
import com.sap.model.WorkDay;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkDayDaoImp extends HibernateDaoSupport implements WorkDayDao {

    private final static String FROM_WORKDAY_AS_U = "from com.sap.model.WorkDay as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(WorkDay workDay) {
        getHibernateTemplate().saveOrUpdate(workDay);
    }

    @Override
    public WorkDay getWorkDayByUser(User user) {
        Integer userId = user.getId();

         return (WorkDay) getHibernateTemplate().find(FROM_WORKDAY_AS_U + "where u.user.id=" + userId.toString()).get(0);
    }

    @Override
    public WorkDay getWorkDayById(Integer id) {
        return (WorkDay) getHibernateTemplate().find(FROM_WORKDAY_AS_U + "where u.id=" + id.toString()).get(0);
    }

    @Override
    public Set<WorkDay> getAllWorkDays() {

        Set<WorkDay> workDaySet = new HashSet<>((List<WorkDay>) getHibernateTemplate().find(FROM_WORKDAY_AS_U ));

        return workDaySet;

    }
}
