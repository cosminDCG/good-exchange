package com.platform.exchange.service;

import com.platform.exchange.model.Message;

import java.util.List;

public interface MessageService {

    Message saveMessage(Message message);

    List<Message> getConversationHistory(String from_id, String to_id);
}
