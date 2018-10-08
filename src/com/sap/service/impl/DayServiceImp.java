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
}
