package com.sap.service.impl;

import com.sap.dao.TeamMessageDao;

import com.sap.model.TeamMessage;
import com.sap.service.TeamMessageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class TeamMessageServiceImp implements TeamMessageService {

    @Resource
    TeamMessageDao teamMessageDao;

    @Override
    public void save(TeamMessage message) {
        teamMessageDao.save(message);
    }

    @Override
    public TeamMessage getMessageById(Integer id) {
        return teamMessageDao.getMessageById(id);
    }

    @Override
    public void deleteMessageById(Integer id) {
        teamMessageDao.deleteMessageById(id);
    }

    @Override
    public List<TeamMessage> getAllMessages() {
        return new ArrayList<>(teamMessageDao.getAllMessages());
    }
}
