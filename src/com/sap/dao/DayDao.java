package com.sap.dao;

import com.sap.model.Day;

import java.util.List;
import java.util.Set;

public interface DayDao {

    void save (Day day);

    Day getDayByID (Integer id);

    Set<Day> getAllDays ();

    Set<Day> getAllHolidays ();

    Set<Day> getAllWeekends ();

}
