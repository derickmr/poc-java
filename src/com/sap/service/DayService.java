package com.sap.service;

import com.sap.model.Day;
import com.sap.model.TeamCalendar;
import com.sap.model.WorkDay;

import java.util.List;

public interface DayService {

    void save (Day day);

    Day getDayByID (Integer id);

    List<Day> getAllDays ();

    List<Day> getAllHolidays ();

    List<Day> getAllWeekends ();

    TeamCalendar generateDays(TeamCalendar teamCalendar);

    void registerWorkDays (List<Day> days, List<WorkDay> workDays);

}
