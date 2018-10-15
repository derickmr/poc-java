package com.sap.service.impl;

import com.sap.model.Day;

import java.util.Comparator;

public class DayComparator implements Comparator<Day> {

    @Override
    public int compare(Day o1, Day o2) {
        if (o1.getDate().isBefore(o2.getDate()))
            return -1;
        else if (o2.getDate().isAfter(o2.getDate()))
            return 1;
        return 0;
    }
}
