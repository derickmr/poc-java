package com.sap.service.impl;

import com.sap.dao.UserDayRelationDao;
import com.sap.model.*;
import com.sap.service.DayService;
import com.sap.service.UserDayRelationService;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class UserDayRelationServiceImp implements UserDayRelationService {

    @Resource
    UserDayRelationDao userDayRelationDao;

    @Resource
    DayService dayService;

    @Override
    public void save(UserDayRelation userDayRelation) {
        userDayRelationDao.save(userDayRelation);
    }

    @Override
    public UserDayRelation getWorkDayByUser(User user) {
        return userDayRelationDao.getWorkDayByUser(user);
    }

    @Override
    public UserDayRelation getWorkDayById(Integer id) {
        return userDayRelationDao.getWorkDayById(id);
    }

    @Override
    public List<UserDayRelation> getAllWorkDays() {
        List<UserDayRelation> workDays = new ArrayList<>(userDayRelationDao.getAllWorkDays());
        return workDays;
    }

    @Override
    public void generateWorkDays (TeamCalendar teamCalendar){

        UserDayRelation userDayRelation;

        for (Day day :
                teamCalendar.getDays()) {
            for (User user :
                    teamCalendar.getTeam().getUsers()) {
                userDayRelation = new UserDayRelation();
                userDayRelation.setShift(Shift.ANY.getShift());
                userDayRelation.setDay(day);
                userDayRelation.setUser(user);
                save(userDayRelation);
            }
        }
    }

    @Override
    public User generateWorkDaysForNewUser(User user, List<TeamCalendar> teamCalendars) {

        UserDayRelation userDayRelation;

        for (TeamCalendar teamCalendar :
                teamCalendars) {
            for (Day day :
                    teamCalendar.getDays()) {
                userDayRelation = new UserDayRelation();
                userDayRelation.setShift(Shift.ANY.getShift());
                userDayRelation.setDay(day);
                userDayRelation.setUser(user);
                save(userDayRelation);
            }
        }
        return user;
    }
}
