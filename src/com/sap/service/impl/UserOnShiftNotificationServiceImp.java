package com.sap.service.impl;

import com.sap.dao.UserOnShiftNotificationDao;
import com.sap.model.Day;
import com.sap.model.Shift;
import com.sap.model.UserOnShiftNotification;
import com.sap.service.UserOnShiftNotificationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class UserOnShiftNotificationServiceImp implements UserOnShiftNotificationService {

    @Resource
    private UserOnShiftNotificationDao userOnShiftNotificationDao;

    @Override
    public void save(UserOnShiftNotification message) {
        userOnShiftNotificationDao.save(message);
    }

    @Override
    public UserOnShiftNotification getNecessityMessageById(Integer id) {
        return userOnShiftNotificationDao.getNecessityMessageById(id);
    }

    @Override
    public List<UserOnShiftNotification> getAllNecessityMessages() {
        return new ArrayList<>(userOnShiftNotificationDao.getAllNecessityMessages());
    }

    @Override
    public void deleteNecessityMessageById(Integer id) {
        userOnShiftNotificationDao.deleteNecessityMessageById(id);
    }

    @Override
    public void deleteMessagesWhichWereAttended(List<UserOnShiftNotification> necessityMessages) {

        if (necessityMessages.isEmpty())
            return;

        Day day = necessityMessages.get(0).getDay();

        for (UserOnShiftNotification necessityMessage :
                necessityMessages) {
            if (necessityMessage.getShift().equals(Shift.DAY.getShift())) {
                necessityMessage.setUsersNeedToReachUsersDesired(necessityMessage.getUsersDesiredOnShift() - day.getUsersOnDay());
                save(necessityMessage);
                if (necessityMessage.getUsersNeedToReachUsersDesired() < 1) {
                    deleteNecessityMessageById(necessityMessage.getId());
                    return;
                }
            } else {
                necessityMessage.setUsersNeedToReachUsersDesired(necessityMessage.getUsersDesiredOnShift() - day.getUsersOnLate());
                save(necessityMessage);
                if (necessityMessage.getUsersNeedToReachUsersDesired() < 1) {
                    deleteNecessityMessageById(necessityMessage.getId());
                    return;
                }
            }
        }
    }
}