package com.platform.exchange.service.impl;

import com.platform.exchange.model.Message;
import com.platform.exchange.model.User;
import com.platform.exchange.repository.MessageRepository;
import com.platform.exchange.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    @Transactional
    public Message saveMessage(Message message) {
        message.setId(UUID.randomUUID());
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public List<Message> getConversationHistory(String from_id, String to_id) {
        List<User> users = Arrays.asList(new User(UUID.fromString(from_id)), new User(UUID.fromString(to_id)));
        return messageRepository.findAllByFromInAndToInOrderByDate(users, users);
    }
}
