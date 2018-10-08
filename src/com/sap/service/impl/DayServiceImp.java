package com.sap.service.impl;

import com.sap.dao.DayDao;
import com.sap.dao.WorkDayDao;
import com.sap.model.Day;
import com.sap.model.TeamCalendar;
import com.sap.model.WorkDay;
import com.sap.service.DayService;
import com.sap.service.WorkDayService;
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
    WorkDayService workDayService;

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

        Integer startDay = teamCalendar.getStartDay();
        Integer endDay = teamCalendar.getEndDay();
        Integer startMonth = teamCalendar.getStartMonth();
        Integer endMonth = teamCalendar.getEndMonth();
        Integer numberOfDays = calculateNumberOfDays(startDay, startMonth, endDay, endMonth);

        List<Day> days = new ArrayList<>();

        Day day;

        while (numberOfDays > 0){
            day = new Day();
            day.setTeamCalendar(teamCalendar);
            day.setDay(startDay);
            day.setMonth(startMonth);
            save(day);
            days.add(day);
            startDay++;

            if (startDay > 30){
                startMonth++;
                startDay = 1;
            }
            numberOfDays--;
        }

        teamCalendar.setDays(new HashSet<>(days));

        return teamCalendar;
    }
	
	@Override
    public void registerWorkDays (List<Day> days, List<WorkDay> workDays){

        Set<WorkDay> workDaySet = new HashSet<>(workDays);
        Set<WorkDay> workDaySetToBeSaved;

        for (Day day:
             days) {
            workDaySetToBeSaved = new HashSet<>();
            for (WorkDay workDay:
                 workDaySet) {
                if (workDay.getDay().equals(day)){
                    workDaySetToBeSaved.add(workDay);
                }
            }
            day.setWorkDays(workDaySetToBeSaved);
            save(day);
        }
    }

    private Integer calculateNumberOfDays(Integer startDay, Integer startMonth, Integer endDay, Integer endMonth) {

        Integer numberOfDays = 0;

        String dateBeforeString = "2018-" + startMonth + "-" + startDay;
        String dateAfterString = "2018-" + endMonth + "-" + endDay;

        LocalDate dateBefore = LocalDate.parse(dateBeforeString, DateTimeFormatter.ofPattern("yyyy-M-d"));
        LocalDate dateAfter = LocalDate.parse(dateAfterString, DateTimeFormatter.ofPattern("yyyy-M-d"));

        numberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(dateBefore, dateAfter));

        return numberOfDays + 1;
        

    }

}