package com.sap.service.impl;

import com.sap.dao.WorkDayDao;
import com.sap.model.*;
import com.sap.service.DayService;
import com.sap.service.WorkDayService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkDayServiceImp implements WorkDayService {

    @Resource
    WorkDayDao workDayDao;

    @Resource
    DayService dayService;

    @Override
    public void save(WorkDay workDay) {
        workDayDao.save(workDay);
    }

    @Override
    public WorkDay getWorkDayByUser(User user) {
        return workDayDao.getWorkDayByUser(user);
    }

    @Override
    public WorkDay getWorkDayById(Integer id) {
        return workDayDao.getWorkDayById(id);
    }

    @Override
    public List<WorkDay> getAllWorkDays() {
        List<WorkDay> workDays = new ArrayList<>(workDayDao.getAllWorkDays());
        return workDays;
    }

    
}
