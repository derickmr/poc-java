package com.sap.dao.impl;

import com.sap.dao.TeamCalendarDao;
import com.sap.model.TeamCalendar;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamCalendarDaoImp extends HibernateDaoSupport implements TeamCalendarDao {

    private final static String FROM_CALENDAR_AS_U = "from com.sap.model.TeamCalendar as u ";

    @Override
    @Transactional(readOnly = false)
    public void save(TeamCalendar calendar) {
        getHibernateTemplate().saveOrUpdate(calendar);
    }

    @Override
    public TeamCalendar getCalendarByID(Integer id) {
        return (TeamCalendar) getHibernateTemplate().find(FROM_CALENDAR_AS_U + "where u.id=" + id.toString()).get(0);

    }

    @Override
    public Set<TeamCalendar> getAllCalendars() {
        Set<TeamCalendar> calendars = new HashSet<>((List<TeamCalendar>) getHibernateTemplate().find(FROM_CALENDAR_AS_U));
        return calendars;
    }
}
