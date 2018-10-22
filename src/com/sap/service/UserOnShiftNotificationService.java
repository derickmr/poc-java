package com.sap.service;

import com.sap.model.UserOnShiftNotification;

import java.util.List;

public interface UserOnShiftNotificationService {

    void save (UserOnShiftNotification message);

    UserOnShiftNotification getNecessityMessageById (Integer id);

    List<UserOnShiftNotification> getAllNecessityMessages ();

    void deleteNecessityMessageById (Integer id);

    void deleteMessagesWhichWereAttended (List<UserOnShiftNotification> necessityMessages);

}
