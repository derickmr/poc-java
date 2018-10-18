package com.sap.service;

import com.sap.model.NecessityMessage;

import java.util.List;

public interface NecessityMessageService {

    void save (NecessityMessage message);

    NecessityMessage getNecessityMessageById (Integer id);

    List<NecessityMessage> getAllNecessityMessages ();

    void deleteNecessityMessageById (Integer id);

    void deleteMessagesWhichWereAttended (List<NecessityMessage> necessityMessages);

}
