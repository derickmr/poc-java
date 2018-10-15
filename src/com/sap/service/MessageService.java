package com.sap.service;

import com.sap.model.Message;

import java.util.List;

public interface MessageService {

    void save (Message message);

    Message getMessageById (Integer id);

    void deleteMessageById (Integer id);

    List<Message> getAllMessages();

}
