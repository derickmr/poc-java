package com.sap.dao;

import com.sap.model.UserOnShiftNotification;

import java.util.Set;

public interface UserOnShiftNotificationDao {

    void save (UserOnShiftNotification message);

    UserOnShiftNotification getNecessityMessageById (Integer id);

    Set<UserOnShiftNotification> getAllNecessityMessages ();

    void deleteNecessityMessageById (Integer id);
}
