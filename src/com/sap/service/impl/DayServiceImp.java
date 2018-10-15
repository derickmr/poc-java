package com.sap.service.impl;

import com.sap.dao.DayDao;
import com.sap.dao.UserDayRelationDao;
import com.sap.model.Day;
import com.sap.model.TeamCalendar;
import com.sap.model.UserDayRelation;
import com.sap.service.DayService;
import com.sap.service.UserDayRelationService;
import org.mockito.cglib.core.Local;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayServiceImp implements DayService {

    @Resource
    DayDao dayDao;

    @Resource
    UserDayRelationService userDayRelationService;

    @Override
    public void save(Day day) {
        dayDao.save(day);
    }

    @Override
    public List<Day> getAllDays() {
        return new ArrayList<>(dayDao.getAllDays());
    }

    @Override
    public List<Day> getAllHolidays() {
        return new ArrayList<>(dayDao.getAllHolidays());
    }

    @Override
    public List<Day> getAllWeekends() {
        return new ArrayList<>(getAllWeekends());
    }

    @Override
    public Day getDayByID(Integer id) {
        return dayDao.getDayByID(id);
    }
	
	@Override
    public TeamCalendar generateDays(TeamCalendar teamCalendar) {

        LocalDate startDate = teamCalendar.getStartDate();
        LocalDate endDate = teamCalendar.getEndDate();
        LocalDate localDateIterator = startDate;

        List<Day> days = new ArrayList<>();
        Day day;

        while (!localDateIterator.isAfter(endDate)){
           day = new Day();
            day.setDate(localDateIterator);
            day.setTeamCalendar(teamCalendar);
            day.setUsersNeededOnDay(teamCalendar.getInitialUsersNeededOnDay());
            day.setUsersNeededOnLate(teamCalendar.getInitialUsersNeededOnLate());
            day.setUsersOnLate(0);
            day.setUsersOnDay(0);
            save(day);
            days.add(day);
            localDateIterator = localDateIterator.plusDays(1);
        }

        teamCalendar.setDays(new HashSet<>(days));

        return teamCalendar;
    }
	
	@Override
    public void registerWorkDays (List<Day> days, List<UserDayRelation> userDayRelations){

        Set<UserDayRelation> userDayRelationSet = new HashSet<>(userDayRelations);
        Set<UserDayRelation> userDayRelationSetToBeSaved;

        for (Day day:
             days) {
            userDayRelationSetToBeSaved = new HashSet<>();
            for (UserDayRelation userDayRelation:
                 userDayRelationSet) {
                if (userDayRelation.getDay().equals(day)){
                    userDayRelationSetToBeSaved.add(userDayRelation);
                }
            }
            day.setUserDayRelations(userDayRelationSetToBeSaved);
            save(day);
        }
    }
}
