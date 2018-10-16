package com.sap.service.impl;

import com.sap.dao.UserDayRelationDao;
import com.sap.model.*;
import com.sap.service.DayService;
import com.sap.service.UserDayRelationService;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class UserDayRelationServiceImp implements UserDayRelationService {

    @Resource
    UserDayRelationDao userDayRelationDao;

    @Resource
    DayService dayService;

    @Override
    public void save(UserDayRelation userDayRelation) {
        userDayRelationDao.save(userDayRelation);
    }

    @Override
    public UserDayRelation getWorkDayByUser(User user) {
        return userDayRelationDao.getWorkDayByUser(user);
    }

    @Override
    public UserDayRelation getWorkDayById(Integer id) {
        return userDayRelationDao.getWorkDayById(id);
    }

    @Override
    public List<UserDayRelation> getAllWorkDays() {
        List<UserDayRelation> workDays = new ArrayList<>(userDayRelationDao.getAllWorkDays());
        return workDays;
    }

    @Override
    public void generateWorkDays (TeamCalendar teamCalendar){

        UserDayRelation userDayRelation;

        for (Day day :
                teamCalendar.getDays()) {
            for (User user :
                    teamCalendar.getTeam().getUsers()) {
                userDayRelation = new UserDayRelation();
                userDayRelation.setShift(Shift.NONE.getShift());
				userDayRelation.setDesiredOriginalShift(Shift.NONE.getShift());
                userDayRelation.setDay(day);
                userDayRelation.setUser(user);
                save(userDayRelation);
            }
        }
    }

    @Override
    public User generateWorkDaysForNewUser(User user, List<TeamCalendar> teamCalendars) {

        UserDayRelation userDayRelation;

        for (TeamCalendar teamCalendar :
                teamCalendars) {
            for (Day day :
                teamCalendar.getDays()) {
                if (!day.getDate().isBefore(LocalDate.now())) {
                    userDayRelation = new UserDayRelation();
                    userDayRelation.setDesiredOriginalShift(Shift.NONE.getShift());
                    userDayRelation.setShift(Shift.NONE.getShift());
                    if (!day.isHoliday() && !day.isWeekend()) {
                        if (Math.random() <= .5)
                            day.setUsersNeededOnDay(day.getUsersNeededOnDay() + 1);
                        else
                            day.setUsersNeededOnLate(day.getUsersNeededOnLate() + 1);
                    }
                    userDayRelation.setDay(day);
                    userDayRelation.setUser(user);
                    dayService.save(day);
                    save(userDayRelation);
                }
            }
        }
        return user;
    }
	
	private boolean isThereSpaceOnShift(Day day, String shift) {
        Integer usersNeeded;
        Integer usersOnShift;

        if (shift.equals(Shift.DAY.getShift())) {
            usersNeeded = day.getUsersNeededOnDay();
            usersOnShift = day.getUsersOnDay();
        } else {
            usersNeeded = day.getUsersNeededOnLate();
            usersOnShift = day.getUsersOnLate();
        }

        if (usersNeeded - usersOnShift <= 0)
            return false;

        return true;
    }
	
	private void removeShift(UserDayRelation userDayRelation) {
        Day day = userDayRelation.getDay();
        if (userDayRelation.getShift().equals(Shift.DAY.getShift())) {
            userDayRelation.setShift(Shift.NONE.getShift());
            save(userDayRelation);
            day.setUsersOnDay(day.getUsersOnDay() - 1);
        }
        else if (userDayRelation.getShift().equals(Shift.LATE.getShift())) {
            userDayRelation.setShift(Shift.NONE.getShift());
            save(userDayRelation);
            day.setUsersOnLate(day.getUsersOnLate() - 1);
        }
        dayService.save(day);

    }
	
	@Override
    public void removeShiftsOfHolidayOrWeekend (List<UserDayRelation> userDayRelations){
        for (UserDayRelation userDayRelation :
                userDayRelations) {
			if (!userDayRelation.isCanWorkAtHolidayOrWeekend() && (userDayRelation.getDay().isHoliday() || userDayRelation.getDay().isWeekend())) {
                removeShift(userDayRelation);
                userDayRelation.setDesiredOriginalShift(Shift.NONE.getShift());
                userDayRelation.setShift(Shift.NONE.getShift());
                save(userDayRelation);
            }
        }
    }
	
	@Override
    public void changeShift(UserDayRelation userDayRelation, String shift) {
        Day day = userDayRelation.getDay();
        List<UserDayRelation> userDayRelationsOnDay = new ArrayList<>(day.getUserDayRelations());

        if (userDayRelation.getDesiredOriginalShift().equals(Shift.ANY.getShift())) {


            if (isThereSpaceOnShift(day, Shift.DAY.getShift())) {
                if (!userDayRelation.getShift().equals(Shift.NONE.getShift()))
                    removeShift(userDayRelation);
                setShift(userDayRelation, Shift.DAY.getShift());
            } else if (isThereSpaceOnShift(day, Shift.LATE.getShift())) {
                if (!userDayRelation.getShift().equals(Shift.NONE.getShift()))
                    removeShift(userDayRelation);
                setShift(userDayRelation, Shift.LATE.getShift());
            }


            save(userDayRelation);
        } else {

            if (isThereSpaceOnShift(day, userDayRelation.getDesiredOriginalShift())) {
                if (!userDayRelation.getShift().equals(Shift.NONE.getShift()))
                    removeShift(userDayRelation);
                setShift(userDayRelation, shift);
            } else {
                UserDayRelation userDayRelationToChange;
                if ((userDayRelationToChange = canUserChangeHisShift(userDayRelationsOnDay, userDayRelation.getUser(), shift)) != null) {

                    changeShitsOfDays(userDayRelation, userDayRelationToChange);

                }
            }

        }

    }
	
	private void changeShitsOfDays(UserDayRelation userDayRelationWithDefinedShift, UserDayRelation userDayRelationWithAnyShift) {
        Day day = userDayRelationWithAnyShift.getDay();
        removeShift(userDayRelationWithAnyShift);
        removeShift(userDayRelationWithDefinedShift);

        setShift(userDayRelationWithDefinedShift, userDayRelationWithDefinedShift.getDesiredOriginalShift());

        if (userDayRelationWithDefinedShift.getShift().equals(Shift.DAY.getShift())) {
            if (day.isWeekend() || day.isHoliday()) {
                if (isThereSpaceOnShift(day, Shift.LATE.getShift())) {
                    setShift(userDayRelationWithAnyShift, Shift.LATE.getShift());
                }
            } else
                setShift(userDayRelationWithAnyShift, Shift.LATE.getShift());
        } else {
            if (day.isWeekend() || day.isHoliday()) {
                if (isThereSpaceOnShift(day, Shift.DAY.getShift())) {
                    setShift(userDayRelationWithAnyShift, Shift.DAY.getShift());
                }
            } else
                setShift(userDayRelationWithAnyShift, Shift.DAY.getShift());
        }
    }
	
	private void setShift(UserDayRelation userDayRelation, String shift) {
        Day day = userDayRelation.getDay();
        userDayRelation.setShift(shift);

        if (shift.equals(Shift.DAY.getShift()))
            day.setUsersOnDay(day.getUsersOnDay() + 1);
        else
            day.setUsersOnLate(day.getUsersOnLate() + 1);
        dayService.save(day);
        save(userDayRelation);
    }
	
	@Override
    public UserDayRelation canUserChangeHisShift(List<UserDayRelation> userDayRelationsOnDay, User user, String wantedShift) {

        for (UserDayRelation userDayRelation :
                userDayRelationsOnDay) {
            if (userDayRelation.getShift() != null && !userDayRelation.getUser().equals(user)) {
                if (userDayRelation.getDesiredOriginalShift().equals(Shift.ANY.getShift()) && userDayRelation.getShift().equals(wantedShift)) {
                    return userDayRelation;
                }
            }
        }
        return null;

    }
	
	@Override
    public void deleteUserDayRelationById(Integer id) {
        userDayRelationDao.deleteUserDayRelationById(id);
    }
	
	@Override
    public void deleteUserDayRelationsFromUser(List<UserDayRelation> userDayRelations) {
        for (UserDayRelation userDayRelation :
                userDayRelations) {
            removeShiftNecessityWhenUserIsDeleted(userDayRelation);
            deleteUserDayRelationById(userDayRelation.getId());
        }
    }
	
	private void removeShiftNecessityWhenUserIsDeleted (UserDayRelation userDayRelation){
        Day day = userDayRelation.getDay();
        if (userDayRelation.getShift().equals(Shift.DAY.getShift())){
            if (day.getUsersNeededOnDay()>0) {
                day.setUsersNeededOnDay(day.getUsersNeededOnDay() - 1);
                dayService.save(day);
            }
        }
        else if (userDayRelation.getShift().equals(Shift.LATE.getShift())){
            if (day.getUsersNeededOnLate()>0) {
                day.setUsersNeededOnLate(day.getUsersNeededOnLate() - 1);
                dayService.save(day);
            }
        }
        else {
            if (day.getUsersNeededOnDay()>0){
                day.setUsersNeededOnDay(day.getUsersNeededOnDay()-1);
                dayService.save(day);
            }
            else if (day.getUsersNeededOnLate()>0){
                day.setUsersNeededOnLate(day.getUsersNeededOnLate()-1);
                dayService.save(day);
            }
        }
        removeShift(userDayRelation);
    }
}
