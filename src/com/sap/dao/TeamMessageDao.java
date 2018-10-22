package com.sap.dao;

import com.sap.model.TeamMessage;

import java.util.Set;

public interface TeamMessageDao {

    void save (TeamMessage message);

    TeamMessage getMessageById (Integer id);

    void deleteMessageById (Integer id);

    Set<TeamMessage> getAllMessages ();

}
