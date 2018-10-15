package com.sap.dao;

import com.sap.model.NecessityMessage;

import java.util.Set;

public interface NecessityMessageDao {

    void save (NecessityMessage message);

    NecessityMessage getNecessityMessageById (Integer id);

    Set<NecessityMessage> getAllNecessityMessages ();

    void deleteNecessityMessageById (Integer id);
}
