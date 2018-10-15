package com.sap.service;

import com.sap.model.Team;
import com.sap.model.TeamCalendar;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TeamCalendarService {

    void save (TeamCalendar calendar);

    TeamCalendar getCalendarByID (Integer id);

    List<TeamCalendar> getAllCalendars();

    void createCalendar (TeamCalendar calendar);

    boolean verifyIfDateIsPossible (TeamCalendar teamCalendar, Team team);

    boolean verifyIfShiftsArePossible (TeamCalendar teamCalendar);

    boolean verifyIfDateMakesPartOfCalendars (LocalDate localDate, List<TeamCalendar> teamCalendars);

    Day getDayByDate (LocalDate date, List<TeamCalendar> teamCalendars);

}
