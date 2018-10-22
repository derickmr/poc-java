package com.sap.service;

import com.sap.model.TeamMessage;

import java.util.List;

public interface TeamMessageService {

    void save (TeamMessage message);

    TeamMessage getMessageById (Integer id);

    void deleteMessageById (Integer id);

    List<TeamMessage> getAllMessages();

}
