package com.sap.service.impl;

import com.sap.dao.DayDao;
import com.sap.dao.TeamCalendarDao;
import com.sap.model.Day;
import com.sap.model.Team;
import com.sap.model.TeamCalendar;
import com.sap.model.UserDayRelation;
import com.sap.service.DayService;
import com.sap.service.TeamCalendarService;
import com.sap.service.UserDayRelationService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeamCalendarServiceImp implements TeamCalendarService {

    @Resource
    TeamCalendarDao teamCalendarDao;

    @Resource
    DayService dayService;

    @Resource
    UserDayRelationService userDayRelationService;

    @Override
    public void save(TeamCalendar calendar) {
        teamCalendarDao.save(calendar);
    }

    @Override
    public TeamCalendar getCalendarByID(Integer id) {
        return teamCalendarDao.getCalendarByID(id);
    }

    @Override
    public List<TeamCalendar> getAllCalendars() {
        List<TeamCalendar> calendars = new ArrayList<>(teamCalendarDao.getAllCalendars());
        return calendars;
    }
	
	@Override
    public void createCalendar(TeamCalendar calendar) {

        save(calendar);

        calendar = dayService.generateDays(calendar);

        save(calendar);

        userDayRelationService.generateWorkDays(calendar);

    }
}
