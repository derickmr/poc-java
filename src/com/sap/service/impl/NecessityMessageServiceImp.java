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
    public void deleteMessagesWhichWereAttended(List<NecessityMessage> necessityMessages) {

        Day day = necessityMessages.get(0).getDay();

        for (NecessityMessage necessityMessage :
                necessityMessages) {
            if (necessityMessage.getShift().equals(Shift.DAY.getShift())) {
                necessityMessage.setUsersNeedToReachUsersDesired(necessityMessage.getUsersDesiredOnShift() - day.getUsersOnDay());
                save(necessityMessage);
                if (necessityMessage.getUsersNeedToReachUsersDesired() < 1)
                    deleteNecessityMessageById(necessityMessage.getId());
                return;

            } else {
                necessityMessage.setUsersNeedToReachUsersDesired(necessityMessage.getUsersDesiredOnShift() - day.getUsersOnLate());
                save(necessityMessage);
                if (necessityMessage.getUsersNeedToReachUsersDesired() < 1)
                    deleteNecessityMessageById(necessityMessage.getId());
                return;
            }
        }
    }
}
