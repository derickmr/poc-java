package com.sap.dao;

import com.sap.model.Message;

import java.util.Set;

public interface MessageDao {

    void save (Message message);

    Message getMessageById (Integer id);

    void deleteMessageById (Integer id);

    Set<Message> getAllMessages ();

}
