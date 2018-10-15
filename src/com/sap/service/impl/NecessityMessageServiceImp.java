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

}
