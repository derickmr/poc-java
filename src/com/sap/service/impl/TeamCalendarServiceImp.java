package com.sap.service.impl;

import com.sap.dao.DayDao;
import com.sap.dao.TeamCalendarDao;
import com.sap.model.Day;
import com.sap.model.Team;
import com.sap.model.TeamCalendar;
import com.sap.model.WorkDay;
import com.sap.service.DayService;
import com.sap.service.TeamCalendarService;
import com.sap.service.WorkDayService;

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
    WorkDayService workDayService;

    @Override
    public void save(TeamCalendar calendar) {
        teamCalendarDao.save(calendar);
    }

    @Override
    public TeamCalendar getCalendarByID(Integer id) {
        return teamCalendarDao.getCalendarByID(id);
    }

    @Override
    public boolean verifyDate(TeamCalendar teamCalendar, Team team) {
        Integer startDay = teamCalendar.getStartDay();
        Integer startMonth = teamCalendar.getStartMonth();
        Integer endDay = teamCalendar.getEndDay();
        Integer endMonth = teamCalendar.getEndMonth();

        if (!isCalendarDatePossible(teamCalendar, new ArrayList<>(team.getTeamCalendars())))
            return false;

        LocalDate localDate = LocalDate.now();
        Integer currentDay = localDate.getDayOfMonth();
        Integer currentMonth = localDate.getMonthValue();

        if (startMonth < currentMonth)
            return false;

        if (startMonth > endMonth)
            return false;

        if (startMonth == endMonth) {
            if (startDay > endDay)
                return false;
        }
        if (startMonth == currentMonth){
            if (startDay < currentDay)
                return false;
        }

        return true;
    }

    @Override
    public List<TeamCalendar> getAllCalendars() {
        List<TeamCalendar> calendars = new ArrayList<>(teamCalendarDao.getAllCalendars());
        return calendars;
    }
	
	@Override
    public boolean isCalendarDatePossible(TeamCalendar calendar, List<TeamCalendar> teamCalendars) {

        Integer startDay = calendar.getStartDay();
        Integer endDay = calendar.getEndDay();
        Integer startMonth = calendar.getStartMonth();
        Integer endMonth = calendar.getEndMonth();

        if (startDay<1)
            return false;
        if (endDay>30)
            return false;
        if (startMonth<1)
            return false;
        if (endMonth>12)
            return false;


        for (TeamCalendar teamCalendar :
                teamCalendars) {
            if (startMonth>=teamCalendar.getStartMonth() && startMonth<teamCalendar.getEndMonth())
                return false;
            if (startMonth>=teamCalendar.getStartMonth() && endMonth<teamCalendar.getEndMonth())
                return false;
            if (startMonth<teamCalendar.getStartMonth() && endMonth>teamCalendar.getEndMonth())
                return false;
            if (startMonth==teamCalendar.getStartMonth()){
                if (startDay>=teamCalendar.getStartDay() && startDay<=teamCalendar.getEndDay())
                    return false;
            }
            if (endMonth==teamCalendar.getEndMonth()){
                if (endDay>=teamCalendar.getStartDay() && endDay<=teamCalendar.getEndDay())
                    return false;
            }
        }

        return true;
    }
}
