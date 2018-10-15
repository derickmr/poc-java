package com.sap.service.impl;

import com.sap.dao.NecessityMessageDao;
import com.sap.model.NecessityMessage;
import com.sap.model.Shift;
import com.sap.service.NecessityMessageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NecessityMessageServiceImp implements NecessityMessageService {

    @Resource
    private NecessityMessageDao necessityMessageDao;

    @Override
    public void save(NecessityMessage message) {
        necessityMessageDao.save(message);
    }

    @Override
    public NecessityMessage getNecessityMessageById(Integer id) {
        return necessityMessageDao.getNecessityMessageById(id);
    }

    @Override
    public List<NecessityMessage> getAllNecessityMessages() {
        return new ArrayList<>(necessityMessageDao.getAllNecessityMessages());
    }

    @Override
    public void deleteNecessityMessageById(Integer id) {
        necessityMessageDao.deleteNecessityMessageById(id);
    }

    @Override
    public List<NecessityMessage> deleteMessagesWhichWereAttended(List<NecessityMessage> necessityMessages) {

        List<NecessityMessage> necessityMessagesWhichWereNotDeleted = new ArrayList<>();

        for (NecessityMessage necessityMessage :
                necessityMessages) {


            if (necessityMessage.getShift().equals(Shift.DAY.getShift())) {
                if (necessityMessage.getDay().getUsersOnDay() > necessityMessage.getUsersOnShiftAtDate()) {
                    deleteNecessityMessageById(necessityMessage.getId());
                }
                else
                    necessityMessagesWhichWereNotDeleted.add(necessityMessage);
            } else {
                if (necessityMessage.getDay().getUsersOnLate() > necessityMessage.getUsersOnShiftAtDate()) {
                    deleteNecessityMessageById(necessityMessage.getId());
                }
                else
                    necessityMessagesWhichWereNotDeleted.add(necessityMessage);
            }
        }
        return necessityMessagesWhichWereNotDeleted;
    }
}
