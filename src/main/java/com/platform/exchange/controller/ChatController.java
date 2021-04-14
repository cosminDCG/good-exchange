package com.platform.exchange.controller;

import com.platform.exchange.model.Message;
import com.platform.exchange.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ChatController {

    private final SimpMessagingTemplate template;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    MessageService messageService;

    @Autowired
    ChatController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(Message message) throws ParseException {
        Date date = new Date();
        message.setDate(dateFormat.parse(dateFormat.format(date)));
        messageService.saveMessage(message);
        this.template.convertAndSend("/chat", message);
    }

    @GetMapping("/api/chat/history")
    public Mono<ResponseEntity<List<Message>>> getChatHistory(@RequestParam String from, @RequestParam String to) {
        return Mono.fromCallable(() -> messageService.getConversationHistory(from, to))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }
}
