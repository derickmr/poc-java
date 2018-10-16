package com.sap.dao;

import com.sap.model.User;
import com.sap.model.UserDayRelation;

import java.util.Set;

public interface UserDayRelationDao {

    void save (UserDayRelation workDay);

    UserDayRelation getWorkDayByUser (User user);

    UserDayRelation getWorkDayById (Integer id);

    Set<UserDayRelation> getAllWorkDays ();
	
	void deleteUserDayRelationById (Integer id);


}
