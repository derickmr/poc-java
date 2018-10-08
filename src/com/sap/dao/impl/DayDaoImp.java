package com.sap.dao.impl;

import com.sap.dao.DayDao;
import com.sap.model.Day;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayDaoImp extends HibernateDaoSupport implements DayDao {

    private final static String FROM_DAY_AS_U = "from com.sap.model.Day as u ";


    @Override
    @Transactional(readOnly = false)
    public void save(Day day) {
        getHibernateTemplate().saveOrUpdate(day);
    }

    @Override
    public Day getDayByID(Integer id) {
        return (Day) getHibernateTemplate().find(FROM_DAY_AS_U + "where u.id="+id.toString()).get(0);
    }

    @Override
    public Set<Day> getAllDays() {
        Set<Day> days = new HashSet<>((List<Day>) getHibernateTemplate().find(FROM_DAY_AS_U));
        return days;
    }

    @Override
    public Set<Day> getAllHolidays() {
        Set<Day> holidays = new HashSet<>((List<Day>) getHibernateTemplate().find(FROM_DAY_AS_U + "where u.holiday=true"));
        return holidays;
    }

    @Override
    public Set<Day> getAllWeekends() {
        Set<Day> weekends = new HashSet<>((List<Day>) getHibernateTemplate().find(FROM_DAY_AS_U + "where u.weekend=true"));
        return weekends;
    }
}
