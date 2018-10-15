package com.sap.service.impl;

import com.sap.dao.MessageDao;
import com.sap.model.Message;
import com.sap.service.MessageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class MessageServiceImp implements MessageService {

    @Resource
    MessageDao messageDao;

    @Override
    public void save(Message message) {
        messageDao.save(message);
    }

    @Override
    public Message getMessageById(Integer id) {
        return messageDao.getMessageById(id);
    }

    @Override
    public void deleteMessageById(Integer id) {
        messageDao.deleteMessageById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(messageDao.getAllMessages());
    }
}
