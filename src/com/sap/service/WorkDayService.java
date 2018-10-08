package com.sap.service;

import com.sap.model.*;

import java.util.List;

public interface WorkDayService {

    void save (WorkDay workDay);

    WorkDay getWorkDayByUser (User user);

    WorkDay getWorkDayById (Integer id);

    List<WorkDay> getAllWorkDays ();

    void generateWorkDays (TeamCalendar teamCalendar);

    User generateWorkDaysForNewUser (User user, List<TeamCalendar> teamCalendars);

}
