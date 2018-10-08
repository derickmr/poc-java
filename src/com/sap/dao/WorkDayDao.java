package com.sap.dao;

import com.sap.model.User;
import com.sap.model.WorkDay;

import java.util.List;
import java.util.Set;

public interface WorkDayDao {

    void save (WorkDay workDay);

    WorkDay getWorkDayByUser (User user);

    WorkDay getWorkDayById (Integer id);

    Set<WorkDay> getAllWorkDays ();

}
