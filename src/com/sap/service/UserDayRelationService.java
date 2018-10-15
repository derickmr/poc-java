package com.sap.service;

import com.sap.model.TeamCalendar;
import com.sap.model.User;
import com.sap.model.UserDayRelation;

import java.util.List;

public interface UserDayRelationService {

    void save (UserDayRelation workDay);

    UserDayRelation getWorkDayByUser (User user);

    UserDayRelation getWorkDayById (Integer id);

    List<UserDayRelation> getAllWorkDays ();

    void generateWorkDays (TeamCalendar teamCalendar);

    User generateWorkDaysForNewUser (User user, List<TeamCalendar> teamCalendars);
	
	UserDayRelation canUserChangeHisShift(List<UserDayRelation> userDayRelationsOnDay, User user, String wantedShift);
	
	void changeShift(UserDayRelation userDayRelation, String shift);

	
}
