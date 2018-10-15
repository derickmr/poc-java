package com.sap.service.impl;

import com.sap.model.TeamCalendar;

import java.util.Comparator;

public class TeamCalendarComparator implements Comparator<TeamCalendar> {

    @Override
    public int compare(TeamCalendar o1, TeamCalendar o2) {
        if (o1.getStartDate().isBefore(o2.getStartDate()))
                    return -1;
                else if (o1.getStartDate().isAfter(o2.getStartDate()))
                    return 1;
                return 0;
    }
}
