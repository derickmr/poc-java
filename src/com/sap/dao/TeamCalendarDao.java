package com.sap.dao;

import com.sap.model.TeamCalendar;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

public interface TeamCalendarDao {

    void save (TeamCalendar calendar);

    TeamCalendar getCalendarByID (Integer id);

    Set<TeamCalendar> getAllCalendars();

}
