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
	
	@Override
    public boolean verifyIfDateIsPossible(TeamCalendar teamCalendar, Team team) {

        LocalDate startDate = teamCalendar.getStartDate();
        LocalDate endDate = teamCalendar.getEndDate();

        if (startDate.isAfter(endDate))
            return false;

        if (startDate.isBefore(LocalDate.now()))
            return false;

        List<TeamCalendar> teamCalendars = new ArrayList<>(team.getTeamCalendars());

        if (teamCalendars.isEmpty())
            return true;

        for (TeamCalendar teamCalendarIterator :
                teamCalendars) {
            LocalDate startDateIterator = teamCalendarIterator.getStartDate();
            LocalDate endDateIterator = teamCalendarIterator.getEndDate();

            if (startDate.isEqual(startDateIterator) || startDate.isEqual(endDateIterator))
                return false;

            if (endDate.isEqual(startDateIterator) || endDate.isEqual(endDateIterator))
                return false;

            if (startDate.isAfter(startDateIterator)) {
                if (endDate.isBefore(endDateIterator))
                    return false;
            }

            if (startDate.isBefore(endDateIterator) && endDate.isAfter(endDateIterator))
                return false;

            if (startDate.isBefore(startDate) && endDate.isAfter(startDateIterator))
                return false;


        }

        return true;
    }
	
	@Override
    public boolean verifyIfShiftsArePossible(TeamCalendar teamCalendar) {

        Integer numberOfUsers = teamCalendar.getTeam().getUsers().size() -1;

        if (numberOfUsers.equals(teamCalendar.getInitialUsersNeededOnDay() + teamCalendar.getInitialUsersNeededOnLate()))
            return true;
        return false;

    }
}
