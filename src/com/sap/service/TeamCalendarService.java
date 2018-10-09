package com.sap.service;

import com.sap.model.Team;
import com.sap.model.TeamCalendar;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TeamCalendarService {

    void save (TeamCalendar calendar);

    TeamCalendar getCalendarByID (Integer id);

    List<TeamCalendar> getAllCalendars();

    //boolean verifyDate (TeamCalendar teamCalendar, Team team);

    void createCalendar (TeamCalendar calendar);

    //boolean isCalendarDatePossible (TeamCalendar calendar, List<TeamCalendar> teamCalendars);

    boolean verifyIfDateIsPossible (TeamCalendar teamCalendar, Team team);

    void createNewCalendar (TeamCalendar teamCalendar);



}
